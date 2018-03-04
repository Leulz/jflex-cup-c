package model;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class BuiltinTypes {
	
	private List<Type> builtins;

	private HashMap<String, List<String>> typeCompatibility;
	
	public BuiltinTypes() {
		this.builtins = new ArrayList<Type>();
		this.typeCompatibility = new HashMap<String, List<String>>();
		
		populate();
	}
	
	private void populate() {
		//initialize builtins and typeCompatibility
		builtins.add(new Type("char"));
		builtins.add(new Type("signed char"));
		builtins.add(new Type("unsigned char"));
		builtins.add(new Type("short"));
		builtins.add(new Type("short int"));
		builtins.add(new Type("signed short"));
		builtins.add(new Type("signed short int"));
		builtins.add(new Type("unsigned short"));
		builtins.add(new Type("unsigned short int"));
		builtins.add(new Type("int"));
		builtins.add(new Type("signed"));
		builtins.add(new Type("signed int"));
		builtins.add(new Type("unsigned"));
		builtins.add(new Type("unsigned int"));
		builtins.add(new Type("long"));
		builtins.add(new Type("long int"));
		builtins.add(new Type("signed long"));
		builtins.add(new Type("signed long int"));
		builtins.add(new Type("unsigned long"));
		builtins.add(new Type("unsigned long int"));
		builtins.add(new Type("float"));
		builtins.add(new Type("double"));
		builtins.add(new Type("long double"));
		builtins.add(new Type("struct"));
		builtins.add(new Type("union"));

		List<String> floatCompTypes = new ArrayList<String>();
    floatCompTypes.add("int");
    floatCompTypes.add("float");

    List<String> intCompTypes = new ArrayList<String>();
    intCompTypes.add("int");
    intCompTypes.add("Integer");

    List<String> charCompTypes = new ArrayList<String>();
    charCompTypes.add("char");


		typeCompatibility.put("float", floatCompTypes);
    typeCompatibility.put("int", intCompTypes);
		typeCompatibility.put("char", charCompTypes);
	}

	public List<Type> getBuiltins() {
		return builtins;
	}

	public HashMap<String, List<String>> getTypeCompatibility() {
		return this.typeCompatibility;
	}
}
