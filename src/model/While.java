package model;

public class While extends Scope {
    private int expressionEvalLabel;
    private boolean expressionEvalLabelSet;
    private StringBuffer code;
    private String falseTemplate;

    public While(String name) {
        super(name);
        expressionEvalLabelSet = false;
        code = null;
    }

    public While(String name, int expressionEvalLabel) {
        this(name);
        setExpressionEvalLabel(expressionEvalLabel);
        this.code = new StringBuffer();
    }

    public void setFalseTemplate(String falseTemplate) {
        this.falseTemplate = falseTemplate;
    }

    public String getFalseTemplate() {
        return falseTemplate;
    }

    public void setExpressionEvalLabel(int expressionEvalLabel) {
        this.expressionEvalLabel = expressionEvalLabel;
        this.expressionEvalLabelSet = true;
    }

    public int getExpressionEvalLabel() {
        return expressionEvalLabel;
    }

    public boolean hasCode() {
        return this.code != null;
    }

    public StringBuffer append(Object obj) {
        return this.code.append(obj);
    }

    public boolean hasExpressionEvalLabel() {
        return this.expressionEvalLabelSet;
    }

    public String codeString() {
        return code.toString();
    }
}
