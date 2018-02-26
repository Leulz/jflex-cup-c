package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Program {
	private HashMap<String, Variable> variables;
	private List<Type> types;
	private ArrayList<Function> functions;
	
	public Program() {
		this.variables = new HashMap<String, Variable>();
		this.types = new ArrayList<Type>();
		this.functions = new ArrayList<Function>();
	}
	

	public HashMap<String, Variable> getVariables() {
		return this.variables;
	}
	
	public List<Type> getTypes() {
		return this.types;
	}
	
	public ArrayList<Function> getFunctions() {
		return this.functions;
	}
}