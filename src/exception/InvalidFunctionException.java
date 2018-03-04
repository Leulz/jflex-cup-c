package exception;

public class InvalidFunctionException extends SemanticException {
  private static final long serialVersionUID = 5002862097331079919L;

  public InvalidFunctionException(String msg){
    super(msg);
  }
}