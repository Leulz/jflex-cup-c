package codegen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import model.Expression;
import model.MethodCall;
import model.Operation;
import model.Parameter;
import model.Scope;
import model.Type;
import model.While;

public class CodeEngine {
    private StringBuffer output;
    private int currentLabel;

    private Map<String, Integer> functionLabel;
    private Map<String, ArrayList<String>> functionArgsRegisters;
    private Map<String, String> functionReturnRegister;

    private String currentMethodIdentifier;

    private Stack<Scope> whileStack;

    private Expression lastResult;
    private String lastResultRegister;
    private int storedLabel;

    public CodeEngine() {
        Registers.getInstance().initialize();
        currentLabel = 100;
        functionLabel = new HashMap<String, Integer>();
        functionArgsRegisters = new HashMap<String, ArrayList<String>>();
        functionReturnRegister = new HashMap<String, String>();
        whileStack = new Stack<Scope>();

        this.output = new StringBuffer();
    }

    /**
     * Adiciona código no buffer
     */
    public StringBuffer appendCode(Object obj) {
        // Se existir while na pilha, concatena no while
        if (!whileStack.isEmpty()) {
            return ((While) whileStack.peek()).append(obj);
        }

        return output.append(obj);
    }

    /**
     * Inicializa o código assembly
     */
    public void initialize() {
        appendCode(String.format("%d: LD SP, 8000\n", newLabel()));
    }

    /**
     * @return um novo label
     */
    public int newLabel() {
        int newLabel = currentLabel;
        currentLabel += 8;

        return newLabel;
    }

    /**
     * @return o próximo label
     */
    public int nextLabel() {
        return currentLabel;
    }

    /**
     * Gera expressão de retorno. Se a expressão e tiver registrador associado, gera:
     * LD RRn, Rn
     * 
     * Ou:
     * LD Rn, e.val
     * LD RRn, Rn
     * 
     */
    public void returnExpression(Expression e) {
        if (!e.hasRegister()) {
            String expressionRegister = Registers.getInstance().newRegister();
            appendCode(Templates.load(newLabel(), expressionRegister, e.getAssemblyValue()));

            e.setRegister(expressionRegister);
        }

        appendCode(
                Templates.load(newLabel(), functionReturnRegister.get(currentMethodIdentifier), e.getAssemblyValue()));
    }

    public String generateIdentifier(MethodCall mc) {
        StringBuilder identifierB = new StringBuilder();

        Type returnType = mc.getType();

        if (returnType != null) {
            identifierB.append(returnType.getName() + "_");
        }

        String id = mc.getValue();
        identifierB.append(id + "_");

        if (mc.getArgs().getExpressionList() != null) {
            for (Expression e : mc.getArgs().getExpressionList()) {
                identifierB.append((e.getType().getName()) + "_");
            }
        }

        identifierB.setLength(identifierB.length() - 1);

        return identifierB.toString();
    }

    public String generateIdentifier(String id, ArrayList<Parameter> params, Type returnType) {
        StringBuilder identifierB = new StringBuilder();

        if (returnType != null) {
            identifierB.append(returnType.getName() + "_");
        }

        identifierB.append(id + "_");

        if (params != null) {
            for (Parameter param : params) {
                identifierB.append((param.getType().getName()) + "_");
            }
        }

        identifierB.setLength(identifierB.length() - 1);

        return identifierB.toString();
    }

    public void functionDeclarationBegin(String id, ArrayList<Parameter> params, Type type) {
        String generatedId = generateIdentifier(id, params, type);

        functionLabel.put(generatedId, nextLabel());

        ArrayList<String> argsRegisters = new ArrayList<String>();
        if (params != null) {
            for (int i = 0; i < params.size(); i++) {
                argsRegisters.add(Registers.getInstance().newArgRegister());
                appendCode(Templates.store(newLabel(), params.get(i).getIdentifier(), argsRegisters.get(i)));
            }
        }

        functionArgsRegisters.put(generatedId, argsRegisters);

        if (!type.getName().equals("void")) { // se for void não tem retorno
            functionReturnRegister.put(generatedId, Registers.getInstance().newReturnRegister());
        }

        currentMethodIdentifier = generatedId;
    }

    /**
     * Esqueleto de código para operações. Avalia se a expressão da esquerda ou da direita já
     * tinha sido avaliada e pega o valor do registrador delas, caso positivo.
     * LD R1, val_le
     * LD R2, val_le
     * OP R3, R1, R2
     * 
     * @param result
     * @param le
     * @param op
     * @param re
     */
    public void operation(Expression result, Expression le, Operation op, Expression re) {
        String leRegister = null, reRegister = null;

        if (lastResult != null && le.getAssemblyValue().equals(lastResult.getAssemblyValue())) {
            leRegister = lastResultRegister;
        } else {
            leRegister = Registers.getInstance().newRegister();
            appendCode(Templates.load(newLabel(), leRegister, le.getAssemblyValue()));
        }

        if (lastResult != null && re.getAssemblyValue().equals(lastResult.getAssemblyValue())) {
            reRegister = lastResultRegister;
        } else {
            reRegister = Registers.getInstance().newRegister();
            appendCode(Templates.load(newLabel(), reRegister, re.getAssemblyValue()));
        }

        String resultRegister = Registers.getInstance().newRegister();
        appendCode(Templates.operation(op, newLabel(), resultRegister, leRegister, reRegister));

        result.setRegister(resultRegister);

        lastResult = result;
        lastResultRegister = resultRegister;
    }

    public void methodCall(MethodCall mc) {
        int argCount = 0;
        String id = generateIdentifier(mc);

        for (Expression e : mc.getArgs().getExpressionList()) {
            appendCode(Templates.load(newLabel(), functionArgsRegisters.get(id).get(argCount++), e.getAssemblyValue()));
        }

        String incPointer = String.format(Templates.operation(Operation.PLUS), newLabel(), "SP", "SP",
                "#" + currentMethodIdentifier + "_size");

        int storeReturnPointerLabel = newLabel();
        String storeReturnPointer = Templates.store(storeReturnPointerLabel, "*SP",
                "#" + String.valueOf(storeReturnPointerLabel + 16));

        String branch = Templates.branch(newLabel(), String.valueOf(functionLabel.get(id)));

        String decPointer = String.format(Templates.operation(Operation.MINUS), newLabel(), "SP", "SP",
                "#" + currentMethodIdentifier + "_size");

        appendCode(incPointer);
        appendCode(storeReturnPointer);
        appendCode(branch);
        appendCode(decPointer);
    }

    public void functionDeclarationEnd() {
        String br = Templates.branch(newLabel(), "*0(SP)");
        appendCode(br);

        Registers.getInstance().resetRegisterCounter();
        lastResult = null;
        lastResultRegister = null;
        currentMethodIdentifier = null;
    }

    public void generateAttribution(String identifier, Object obj) {
        if (obj instanceof MethodCall) {
            generateMethodCallAttribution(identifier, (MethodCall) obj);
        } else if (obj instanceof Expression) {
            generateOperationAttribution(identifier, (Expression) obj);
        } else {

        }

        Registers.getInstance().resetRegisterCounter();
    }

    public void generateAttribution(String id, Operation op, Expression e) {
        String register = Registers.getInstance().newRegister();

        String value;

        if (lastResultRegister != null) {
            value = lastResultRegister;
            lastResultRegister = null;
        } else {
            value = e.getAssemblyValue();
        }

        appendCode(Templates.load(newLabel(), register, id));
        switch (op) {
        case PLUSEQ:
            appendCode(Templates.operation(Operation.PLUS, newLabel(), register, register, value));
            break;
        case MINUSEQ:
            appendCode(Templates.operation(Operation.MINUS, newLabel(), register, register, value));
            break;
        case MULTEQ:
            appendCode(Templates.operation(Operation.MULT, newLabel(), register, register, value));
            break;
        case DIVEQ:
            appendCode(Templates.operation(Operation.DIV, newLabel(), register, register, value));
            break;
        case MODEQ:
            appendCode(Templates.operation(Operation.MOD, newLabel(), register, register, value));
            break;
        default:

        }
        appendCode(Templates.store(newLabel(), id, register));

        Registers.getInstance().resetRegisterCounter();
    }

    private void generateMethodCallAttribution(String identifier, MethodCall obj) {
        appendCode(Templates.store(newLabel(), identifier, functionReturnRegister.get(generateIdentifier(obj))));
    }

    private void generateOperationAttribution(String identifier, Expression exp) {
        if (lastResult == null) {
            String register = Registers.getInstance().newRegister();
            appendCode(Templates.load(newLabel(), register, exp.getAssemblyValue(true)));
            exp.setRegister(register);
            appendCode(Templates.store(newLabel(), identifier, exp.getAssemblyValue()));
            return;
        }

        if (exp.hasRegister()) {
            if (!exp.getRegister().equals(lastResultRegister)) {
                System.out.println("Há inconsistência!!!");
            }
            appendCode(Templates.store(newLabel(), identifier, exp.getRegister()));
        } else {

            String register = Registers.getInstance().newRegister();
            appendCode(Templates.load(newLabel(), register, exp.getAssemblyValue(true)));
            exp.setRegister(register);
            appendCode(Templates.store(newLabel(), identifier, exp.getAssemblyValue()));
        }

        Registers.getInstance().resetRegisterCounter();
    }

    public void storeLabel() {
        this.storedLabel = currentLabel;
    }

    /**
     * Gera código do while
     * @param e expressão booleana
     * @return objeto While
     */
    public While whileStatement(Expression e) {
        While w = new While("while", storedLabel); // begin
        whileStack.add(w);

        if (e instanceof MethodCall) {
            e.setRegister(functionReturnRegister.get(generateIdentifier((MethodCall) e)));
        }

        if (e.hasOperation()) {
            switch (e.getOperation()) {
            case LT:
                w.setFalseTemplate(Templates.branchBGTZ(newLabel(), e.getRegister()));
                break;
            case GT:
                w.setFalseTemplate(Templates.branchBLTZ(newLabel(), e.getRegister()));
                break;
            case LTEQ:
                w.setFalseTemplate(Templates.branchBGEZ(newLabel(), e.getRegister()));
                break;
            case GTEQ:
                w.setFalseTemplate(Templates.branchBLEZ(newLabel(), e.getRegister()));
                break;
            case EQEQ:
                w.setFalseTemplate(Templates.branchBNEZ(newLabel(), e.getRegister()));
                break;
            default:
                break;

            }
        } else {
            w.setFalseTemplate(Templates.branchBLTZ(newLabel(), e.getAssemblyValue()));
        }

        return w;
    }

    public void whileStatementEnd() {
        While w = null;
        if (whileStack.peek() instanceof While) {
            w = (While) whileStack.pop();
        } else {
            return;
        }

        w.append(Templates.branch(newLabel(), String.valueOf(w.getExpressionEvalLabel())));
        String branch = String.format(w.getFalseTemplate(), currentLabel);

        appendCode(branch);
        appendCode(w.codeString());
    }

    public String toAssemblyString() {
        return output.toString();
    }
}