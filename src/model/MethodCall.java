package model;

public class MethodCall extends Expression {

    private ExpressionList args;

    public MethodCall(String name, ExpressionList args) {
        super(null, name);

        this.args = args;
    }

    public MethodCall(String name) {
        super(null, name);

        this.args = new ExpressionList();
    }

    public ExpressionList getArgs() {
        return args;
    }
}
