package model;

public class Expression implements ExpressionIF, Typeable {
	private Type type;
	private String value;
	private boolean returnFlag;
	private String register;
	private Operation operation;
	
	public Expression(Type t) {
		this.type = t;
	}
	
	public Expression(Type t, String value) {
		this.type = t;
		this.value = value;
		this.returnFlag = false;
	}

	public Expression(Operation op, Type t, String value) {
		this.type = t;
		this.value = value;
		this.returnFlag = false;
		this.operation = op;
	}
	
	public Operation getOperation() {
		return operation;
	}
	
	public void setOperation(Operation operation) {
		this.operation = operation;
	}
	
	public boolean hasOperation() {
		return this.operation != null;
	}
	
	public String getRegister() {
		return register;
	}
	
	public void setRegister(String register) {
		this.register = register;
	}
	
	public boolean hasRegister() {
		return register != null;
	}
	
	public Type getType() {
		return type;
	}
	
	public String getValue() {
		return value;
	}

	public void setType(Type type){
        this.type = type;
    }
	
	public void setReturn(boolean returnFlag) {
		this.returnFlag = returnFlag;
	}
	
	public boolean isReturn() {
		return returnFlag;
	}
	
	public boolean isNumeric() {
		return getType().getName().equals("int")
				|| getType().getName().equals("float")
				|| getType().getName().equals("long")
				|| getType().getName().equals("double")
				|| getType().getName().equals("byte")
				|| getType().getName().equals("short");
	}

    public boolean isString() {
        return getType().getName().equals("String");
    }
    
    public boolean isBoolean() {
    	return getType().getName().equals("boolean");
    }

	public String toString(){
		return "Expression of type; " + getType();
	}

	@Override
	public boolean isExpressionList() {
		return false;
	}
}