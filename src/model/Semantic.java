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
	
	public void commitDeclaredVariables(Type type) throws Exception {
		for (Variable variable : scopedVars) {
			System.out.println("variable is " + variable.getIdentifier() + " and type is " + type.getName());
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
			return getCurrentScope().getVariable().get(variableName);
		} else {
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

	private void pushScope(Scope scope) {
		scopeStack.push(scope);
	}
}
