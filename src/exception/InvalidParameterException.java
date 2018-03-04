package exception;

public class InvalidParameterException extends SemanticException {
  private static final long serialVersionUID = 7463457546544675921L;

  public InvalidParameterException(String msg){
    super(msg);
  }
}