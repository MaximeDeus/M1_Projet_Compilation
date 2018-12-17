package asml;

/**
 *
 * @author liakopog
 */
class Asmt implements Visitable {

    public final Exp_asml e;     //exp
    public final Boolean paren;  //LPAREN asmt RPAREN
    public final Asmt asmt;      //LET IDENT EQUAL exp IN asmt
    public final Ident ident;    //LET IDENT EQUAL exp IN asmt

    public Asmt(Ident ident, Exp_asml e, Asmt asmt) {
        this.e = e;
        this.paren = false;
        this.asmt = asmt;
        this.ident = ident;
    }

    public Asmt(Exp_asml e) {
        this.e = e;
        this.paren = false;
        this.asmt = null;
        this.ident = null;

    }

    public Asmt(Asmt asmt) {
        this.paren = true;
        this.asmt = asmt;
        this.ident = null;
        this.e = null;

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
