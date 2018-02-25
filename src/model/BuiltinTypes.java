package model;

import java.util.ArrayList;
import java.util.List;

public class BuiltinTypes {
	
	private List<Type> builtins;
	
	public BuiltinTypes() {
		this.builtins = new ArrayList<Type>();
		
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
	}

	public List<Type> getBuiltins() {
		return builtins;
	}
}
