package model;

public class Semantic {
	
	private BuiltinTypes builtinTypes = new BuiltinTypes();

	public boolean checkTypes(Type leftType, Type rightType) {
		if (leftType.equals(rightType)) {
			return true;
		} else {
			//all types are compatible in C
			if ((leftType.getName().equals("struct") ^ rightType.getName().equals("struct"))
					|| (leftType.getName().equals("union") ^ rightType.getName().equals("union"))) {
				return false;
			} else {
				return true;
			}
		}
	}
}
