package okalm.ocamlcompiler.java.asml;

/**
 *
 * @author liakopog
 */
public enum Tokens {
	LPAREN("("),
     RPAREN(")"),
     PLUS("+"),
     EQUAL("="),
     FEQUAL("=."),
     LE("<="),
     FLE("<=."),
     GE(">="),
     IF("if"),
     THEN("then"),
     ELSE("else"),
     LET("let"),
     IN("in"),
     DOT("."),
     NEG("neg"),
     FNEG("fneg"),
     MEM("mem"),
     FMUL("fmul"),
     FDIV("fdiv"),
     FSUB("fsub"),
     FADD("fadd"),
     ASSIGN("<-"),
     ADD("add"),
     SUB("sub"),
     CALL("call"),
     NEW("new"),
     NOP("nop"),
     APPCLO("apply_closure"),
     UNDERSC("_");
     
	private final String string;

	private Tokens(String s) {
		this.string = s;
	}

	public String getToken() {
		return string;
	}
}
