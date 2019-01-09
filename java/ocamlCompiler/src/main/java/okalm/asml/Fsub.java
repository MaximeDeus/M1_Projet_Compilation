package okalm.asml;

/**
 *
 * @author liakopog
 */
public class Fsub extends Exp_asml {

    Exp_asml ident1;
    Exp_asml ident2;

    public Fsub(Exp_asml ident1, Exp_asml ident2) {
        this.ident1 = ident1;
        this.ident2 = ident2;
    }

    @Override
    public <E> E accept(AsmlObjVisitor<E> v) {
        return v.visit(this);
    }

    @Override
    public void accept(AsmlVisitor v) {
        v.visit(this);
    }

    @Override
    public <E> E accept(AsmlErrorVisitor<E> v) throws Exception {
        return v.visit(this);

    }
}
