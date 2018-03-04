package exception;

public class InvalidOperationException extends SemanticException {
  private static final long serialVersionUID = 6887810813579891659L;

  public InvalidOperationException(String string) {
    super(string);
  }
}