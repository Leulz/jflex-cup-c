import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
	}

	public List<Type> getBuiltins() {
		return builtins;
	}

	public HashMap<String, List<String>> getTypeCompatibility() {
		return typeCompatibility;
	}
}
