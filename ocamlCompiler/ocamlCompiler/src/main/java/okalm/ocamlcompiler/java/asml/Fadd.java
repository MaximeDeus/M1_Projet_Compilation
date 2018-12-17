package okalm.ocamlcompiler.java.asml;

/**
 *
 * @author liakopog
 */
public class Fadd extends Exp_asml {

    public final Ident ident1;
    public final Ident ident2;

    public Fadd(Ident ident1, Ident ident2) {
        this.ident1 = ident1;
        this.ident2 = ident2;
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
