package okalm.ocamlcompiler.java.asml;

/**
 *
 * @author liakopog
 */
public class Neg extends Exp_asml {

    public final Exp_asml ident;

    public Neg(Exp_asml ident) {
        this.ident = ident;
    }

    @Override
    public <E> E accept(AsmlObjVisitor<E> v) {
        return v.visit(this);
    }

    @Override
    public void accept(AsmlVisitor v) {
        v.visit(this);
    }

}
