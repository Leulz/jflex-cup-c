package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import exception.*;

public class Semantic {
	
	private Program currentProgram = new Program();
	
	private BuiltinTypes builtinTypes = new BuiltinTypes();
	
	private List<Variable> scopedVars = new ArrayList<Variable>();
	
	private Stack<Scope> scopeStack = new Stack<Scope>();

	public boolean checkTypes(Type leftType, Type rightType) {
		if (leftType.equals(rightType)) {
			return true;
		} else {
			if ((leftType.getName().equals("struct") ^ rightType.getName().equals("struct"))
					|| (leftType.getName().equals("union") ^ rightType.getName().equals("union"))) {
				return false;
			} else {
				return true;
			}
		}
	}

	public void validateVariableName(String variableName) throws InvalidVariableException {
		if (!checkVariableExistence(variableName)) {
			throw new InvalidVariableException("A variavel chamada " + variableName + " não existe!");
		}
	}

	public void exitCurrentScope() throws InvalidFunctionException {
		Scope scoped = scopeStack.pop();
		checkFunctionTypeConsistency(scoped.getName(), ((Function) scoped).getDeclaredReturnType(), null);
	}

	private void checkFunctionTypeConsistency(String functionName, Type declaredType, Expression exp)
			throws InvalidFunctionException {
		if (exp == null && declaredType.equals(new Type("void"))) {
			return;
		}
		if (exp == null && !declaredType.equals(new Type("void"))) {
			throw new InvalidFunctionException("Falta no método '" + functionName + "' uma declaração de retorno.");
		}
		if (!declaredType.equals(new Type("void"))) {
			if (!exp.isReturn()) {
				throw new InvalidFunctionException("Falta no método '" + functionName + "' uma declaração de retorno.");
			}
			if (!declaredType.equals(exp.getType()) && !checkTypes(declaredType, exp.getType())) {
				throw new InvalidFunctionException("A função " + functionName + " não retornou o tipo esperado: "
						+ declaredType + ". Ao invés disso, está retornando o tipo: " + exp.getType());
			}
		} else {
			if (exp.isReturn()) {
				if (exp.getType() != null) {
					throw new InvalidFunctionException("A função '" + functionName
							+ "' com retorno declarado 'void' não deve ter retorno. Ao invés disso, está retornando o tipo: "
							+ exp.getType() + ".");
				}
			}

		}
	}
	
	public void commitDeclaredVariables(Type type) throws Exception {
		for (Variable variable : scopedVars) {
			variable.setType(type);
			addVariable(variable);
		}

		scopedVars = new ArrayList<Variable>();
	}
	
	private void checkProgramVariable(Variable variable) throws Exception {
		if (checkVariableExistenceGlobal(variable.getIdentifier())) {
			throw new InvalidVariableException("a variável '" + variable.getIdentifier() + "' ("
		             + variable.getType().getName() + " " + variable.getIdentifier() + ") já foi declarada neste escopo");
		}
		if (!checkValidExistingType(variable.getType())) {
			if (!variable.getValue().getType().getName().equals("null")) {
				throw new InvalidTypeException("o tipo '" + variable.getType().getName() + "' da variavel "
						+ variable.getIdentifier() + " é desconhecido");
			}
		}
	}
	
	public boolean checkVariableExistence(String variableName) {
		if (!scopeStack.isEmpty() && getCurrentScope().getVariable().get(variableName) != null) {
			return true;
		} else if (getCurrentProgram().getVariables().get(variableName) != null) {
			return true;
		} else {
			return false;
		}
	}

	private Program getCurrentProgram() {
		return this.currentProgram;
	}
	
	public Scope getCurrentScope() {
		return scopeStack.peek();
	}
	
	private void checkVariable(Variable variable) throws Exception {
		if (variableExistsCurrentScope(variable.getIdentifier())) {
			throw new InvalidVariableException("a variável '" + variable.getIdentifier() + "' ("
		            + variable.getType().getName() + " " + variable.getIdentifier() + ") já foi declarada neste escopo");
		}
		if (!checkValidExistingType(variable.getType())) {
			throw new InvalidTypeException("o tipo '" + variable.getType().getName() + "' da variavel "
					+ variable.getIdentifier() + " é desconhecido");
		}
	}
	
	public boolean checkValidExistingType(Type type) {
		return builtinTypes.getBuiltins().contains(type) /*|| getCurrentClass().getTypes().contains(type)*/;
	}

	public boolean variableExistsCurrentScope(String variableName) {
		if (!scopeStack.isEmpty() && getCurrentScope().getVariable().get(variableName) != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean checkVariableExistenceGlobal(String variableName) {
		return getCurrentProgram().getVariables().get(variableName) != null ? true : false;
	}
	
	private void addVariable(Variable variable) throws Exception {
		if (scopeStack.isEmpty()) {
			checkProgramVariable(variable);
			getCurrentProgram().getVariables().put(variable.getIdentifier(), variable);
		} else {
			checkVariable(variable);
			getCurrentScope().addVariable(variable);
		}

		if (variable.getValue() != null) {
			checkVariableAttribution(variable.getIdentifier(), variable.getValue());
		}
	}
	
	public void checkVariableAttribution(String id, Expression expression) throws IncompatibleTypeException, InvalidTypeException, InvalidVariableException {
		if (!checkVariableExistence(id)) {
			throw new InvalidVariableException(
					"A variavel chamada " + id + " e com valor " + expression.getValue() + " não existe!");
		}
		if (!checkValidExistingType(expression.getType())) {
			if (!expression.getType().getName().equals("null")) {
				throw new InvalidTypeException("O tipo " + expression.getType().getName()
						+ " atribuido a variavel " + id + " não existe!");
			}
		}
		Type identifierType = findVariableByIdentifier(id).getType();
		if (!checkTypes(identifierType, expression.getType())) {
			throw new IncompatibleTypeException(identifierType, expression.getType());
		}
	}
	
	public Variable findVariableByIdentifier(String variableName) {
		if (!scopeStack.isEmpty() && getCurrentScope().getVariable().get(variableName) != null) {
			Variable a = getCurrentScope().getVariable().get(variableName);
			return getCurrentScope().getVariable().get(variableName);
		} else {
			Variable a = getCurrentProgram().getVariables().get(variableName);
			return getCurrentProgram().getVariables().get(variableName);
		}
	}

	private void checkParameters(ArrayList<Parameter> params) throws InvalidParameterException {
		for (int i = 0; i < params.size(); i++) {
			for (int k = i + 1; k < params.size(); k++) {
				if (params.get(i).getIdentifier().equals(params.get(k).getIdentifier())) {
					throw new InvalidParameterException(
							"O parâmetro: " + params.get(k).getIdentifier() + " já foi definido.");
				}
			}
		}
	}

	public void validateFunction(String functionName, ArrayList<Parameter> params, Type declaredType) throws Exception {
		if (declaredType == null) {
			throw new InvalidFunctionException("A função " + functionName
					+ " está sem declaração do tipo de retorno ou não possui declaração de retorno");
		}
		
		Function newFunction = new Function(functionName, params);
		newFunction.setDeclaredReturnedType(declaredType);
		if (functionExists(newFunction)) {
			if (params != null) {
				checkParameters(params);
			}
			addFunction(newFunction);
		}
	}
	
	public void addScopedVar(Variable v) {
		this.scopedVars.add(v);
	}
	public void addFunction(Function f) throws Exception {
		getCurrentProgram().getFunctions().add(f);
		pushScope(f);
		if (f.getParams() != null) {
			for (Parameter p : f.getParams()) {
				addVariable((Variable) p);
			}
		}
	}

	public boolean functionExists(Function other) throws InvalidFunctionException {
		for (Function fun : getCurrentProgram().getFunctions()) {
			if (fun.getName().equals(other.getName())) {
				if (other.equals(fun)) {
					throw new InvalidFunctionException(
							"A função '" + other.getName() + "' já foi declarada com esses mesmos parâmetros.");
				}
				
				if (other.haveSameParameters(fun) && !other.getDeclaredReturnType().equals(fun.getDeclaredReturnType())) {
					throw new InvalidFunctionException(
							"A função '" + other.getName() + "' com os mesmos parâmetros já foi declarada com o tipo de retorno " + fun.getDeclaredReturnType().getName());
				}
			}
		}
		return true;
	}

	public Expression getExpression(Expression le, Operation md, Expression re)
			throws InvalidTypeException, InvalidOperationException {
		if (re == null || le == null || checkTypes(le.getType(), re.getType())
				|| checkTypes(re.getType(), le.getType())) {
			switch (md) {
			case AND:
				return new Expression(getMajorType(le.getType(), re.getType()),
						le.getValue() + " " + md + " " + re.getValue());
			case OR:
				return new Expression(getMajorType(le.getType(), re.getType()),
						le.getValue() + " " + md + " " + re.getValue());
			case GTEQ:
				return new Expression(md, new Type("boolean"), le.getValue() + " " + md + " " + re.getValue());
			case EQEQ:
				return new Expression(md, new Type("boolean"), le.getValue() + " " + md + " " + re.getValue());
			case LTEQ:
				return new Expression(md, new Type("boolean"), le.getValue() + " " + md + " " + re.getValue());
			case LT:
				return new Expression(md, new Type("boolean"), le.getValue() + " " + md + " " + re.getValue());
			case GT:
				return new Expression(md, new Type("boolean"), le.getValue() + " " + md + " " + re.getValue());
			case NOTEQ:
				return new Expression(new Type("boolean"), le.getValue() + " " + md + " " + re.getValue());
			case NOT:
				return new Expression(new Type("boolean"));
			case XOREQ:
				return new Expression(new Type("boolean"));
			case XOR:
				return new Expression(getMajorType(le.getType(), re.getType()),
						le.getValue() + " " + md + " " + re.getValue());
			case OROR:
				return new Expression(new Type("boolean"), le.getValue() + " " + md + " " + re.getValue());
			case ANDAND:
				return new Expression(new Type("boolean"), le.getValue() + " " + md + " " + re.getValue());
			case ANDEQ:
				return new Expression(new Type("boolean"));
			case OREQ:
				return new Expression(new Type("boolean"));
			case MINUS:
				return new Expression(getMajorType(le.getType(), re.getType()),
						le.getValue() + " " + md + " " + re.getValue());
			case MULT:
				return new Expression(getMajorType(le.getType(), re.getType()),
						le.getValue() + " " + md + " " + re.getValue());
			case MOD:
				return new Expression(getMajorType(le.getType(), re.getType()),
						le.getValue() + " " + md + " " + re.getValue());
			case PLUS:
				return new Expression(getMajorType(le.getType(), re.getType()),
						le.getValue() + " " + md + " " + re.getValue());
			case DIV:
				return new Expression(getMajorType(le.getType(), re.getType()),
						le.getValue() + " " + md + " " + re.getValue());
			case DIVEQ:
				return new Expression(getMajorType(le.getType(), re.getType()));
			case PLUSEQ:
				return new Expression(getMajorType(le.getType(), re.getType()));
			case MINUSEQ:
				return new Expression(getMajorType(le.getType(), re.getType()));
			case MULTEQ:
				return new Expression(getMajorType(le.getType(), re.getType()));
			case PLUSPLUS:
				if (le != null && re == null)
					return new Expression(le.getType(), le.getValue() + " " + md);
				return new Expression(re.getType(), md + " " + re.getValue());
			case MINUSMINUS:
				if (le != null && re == null)
					return new Expression(le.getType(), le.getValue() + " " + md);
				return new Expression(re.getType(), md + " " + re.getValue());
			default:
				throw new InvalidOperationException("A operação '" + md + "' não existe!");
			}
		}

		throw new InvalidTypeException("Operação formada pela expressão '" + le.getValue() + " " + md + " "
				+ re.getValue() + "' não é permitida!");
	}

	private Type getMajorType(Type type1, Type type2) {
		return builtinTypes.getTypeCompatibility().get(type1.getName()).contains(type2.getName()) ? type1 : type2;
	}

	private void pushScope(Scope scope) {
		scopeStack.push(scope);
	}
}
