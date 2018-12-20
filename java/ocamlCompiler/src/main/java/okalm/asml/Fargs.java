package okalm.asml;

/**
 *
 * @author liakopog
 */
class Fargs implements Visitable {

    public final Exp_asml ident;    //case IDENT
    public final Exp_asml fargs;    //case IDENT formal_args
    public final Boolean estNIL;    //case NIL

    public Fargs(Boolean estNIL) {
        this.estNIL = estNIL;
        this.fargs = null;
        this.ident = null;

    }

    public Fargs(Exp_asml ident, Exp_asml fargs, Boolean estNIL) {
        this.ident = ident;
        this.fargs = fargs;
        this.estNIL = estNIL;
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
