import java.util.List;

public class Semantic {
	
	private BuiltinTypes builtinTypes = new BuiltinTypes();

	public boolean checkTypes(Type leftType, Type rightType) {
		if (leftType.equals(rightType)) {
			return true;
		} else {
			List<String> tipos = builtinTypes.getTypeCompatibility().get(leftType.getName());
			if (tipos == null)
				return false;
			return tipos.contains(rightType.getName());
		}
	}
}
