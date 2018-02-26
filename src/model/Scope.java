package model;

import java.util.HashMap;
import java.util.Map;

public abstract class Scope {

	private HashMap<String, Variable> variables;
	private HashMap<String, Type> types;

	String name;
	
	public Scope(String name) {
		this.name = name;
		variables = new HashMap<String, Variable>();
	}

	public Map<String, Variable> getVariable() {
		return variables;
	}

	public void addVariable(Variable v) {
		this.variables.put(v.getIdentifier(), v);
	}

	public void addType(Type t) {
		this.types.put(t.getName(), t);
	}

	public Map<String, Type> getTypes() {
		return types;
	}

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Scope other = (Scope) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }
}