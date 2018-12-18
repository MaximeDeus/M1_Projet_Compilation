package okalm.ocamlcompiler.java.asml;

/**
 *
 * @author liakopog
 */
class Fargs implements Visitable {

    public final Ident ident;    //case IDENT
    public final Fargs fargs;    //case IDENT formal_args
    public final Boolean estNIL;    //case NIL

    public Fargs(Boolean estNIL) {
        this.estNIL = estNIL;
        this.fargs = null;
        this.ident = null;

    }

    public Fargs(Ident ident, Fargs fargs) {
        this.ident = ident;
        this.fargs = fargs;
        this.estNIL = false;

    }

    public Fargs(Ident ident) {
        this.ident = ident;
        this.fargs = null;
        this.estNIL = false;

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
