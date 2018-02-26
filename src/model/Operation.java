package model;

public enum Operation {
    PLUS("+"),
    MINUS("-"),
    PLUSEQ("+="),
    MINUSEQ("-="),
    MULT("*"),
    MULTEQ("*="),
    DIV("/"),
    DIVEQ("/="),
    PLUSPLUS("++"),
    MINUSMINUS("--"),
    MOD("%"),
    MODEQ("%="),
    EQEQ("=="),
    GTEQ(">="),
    LTEQ("<="),
    NOTEQ("!="),
    XOREQ("^="),
    LT("<"),
    GT(">"),
    OR("|"),
    AND("&"),
    NOT("!"),
    XOR("^"),
    ANDEQ("&="),
    OROR("||"),
    OREQ("|="),
    ANDAND("&&"),
    GTGT(">>"),
    LTLT("<<"),
    GTGTEQ(">>="),
    LTLTEQ("<<="),
    PTRIGHT("->"),
    COMP("~");
    
	private String value;
	
    private Operation(String value) {
    		this.value = value;
    }
    
    public String getValue() {
    		return this.value;
    }
}