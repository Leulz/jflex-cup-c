package exception;

public class SemanticException extends Exception {
	private static final long serialVersionUID = 4525478005346519174L;
	
	private String message;

	public SemanticException() {
		super();
	}
	
	public SemanticException(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
	
	protected void setMessage(String message) {
		this.message = message;
	}
}