package model;

import org.apache.commons.lang3.StringUtils;

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

	public String getAssemblyValue(boolean exceptRegister) {
		if (this.hasRegister() && !exceptRegister) {
			return this.register;
		}

		if (StringUtils.isNumeric(getValue())) {
			return "#" + this.getValue();
		}

		if (getType().equals(new Type("String"))) {
			return "\"" + this.getValue() + "\"";
		}

		if (this.value != null) {
			if (this.value.equals("true")) {
				return "#1";
			} else if (this.value.equals("false")) {
				return "#0";
			}
		}

		return this.value;
	}

	public String getAssemblyValue() {
		return getAssemblyValue(false);
	}

	@Override
	public boolean isExpressionList() {
		return false;
	}
}