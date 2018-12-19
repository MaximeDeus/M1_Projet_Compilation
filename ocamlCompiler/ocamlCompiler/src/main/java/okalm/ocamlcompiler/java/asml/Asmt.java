package okalm.ocamlcompiler.java.asml;

/**
 *
 * @author liakopog
 */
public class Asmt extends Exp_asml {

    private Exp_asml ident;    //LET IDENT EQUAL exp IN asmt
    public Exp_asml e;     //exp
    public Boolean paren;  //LPAREN asmt RPAREN
    public Exp_asml asmt;      //LET IDENT EQUAL exp IN asmt

    public Asmt(Exp_asml ident, Exp_asml e, Exp_asml asmt, Boolean paren) {
        this.e = e;
        this.paren = paren;
        this.asmt = asmt;
        this.ident = ident;
    }

    public void setIdent(Exp_asml ident) {
        this.ident = ident;
    }

    public Exp_asml getIdent() {
        return ident;
    }

    @Override
    public void accept(AsmlVisitor v) {
        v.visit(this);
    }

    @Override
    public <E> E accept(AsmlObjVisitor<E> v) {
        return v.visit(this);
    }

}
