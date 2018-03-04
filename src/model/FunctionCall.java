package model;

public class FunctionCall extends Expression {
  
  private ExpressionList args;
  
  public FunctionCall(String name, ExpressionList args) {
    super(null, name);
    
    this.args = args;
  }

  public FunctionCall(String name) {
    super(null, name);
    
    this.args = new ExpressionList();
  }

  public ExpressionList getArgs() {
    return args;
  }
}