package exception;

public class InvalidTypeException extends SemanticException {

	private static final long serialVersionUID = -2095219498059407802L;

	public InvalidTypeException(String message){
		 super(message);
	}
}
