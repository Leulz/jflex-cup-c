package exception;

import model.Type;

public class IncompatibleTypeException extends SemanticException {
	
	private static final long serialVersionUID = -9060792443878645624L;

	public IncompatibleTypeException(Type a, Type b) {
		StringBuilder sb = new StringBuilder("Tipos incompatíveis: ");
		sb.append(a.getName());
		sb.append(" não pode ser convertido para ");
		sb.append(b.getName());

		this.setMessage(sb.toString());
	}
}