package okalm.asml;

/**
 *
 * @author liakopog
 */
public class Ident extends IdentOrImm {

    public String ident;

    public Ident(String ident) {
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

    @Override
    public <E> E accept(AsmlErrorVisitor<E> v) throws Exception {
        return v.visit(this);
    }

    @Override
    public String toString() {
        return ident;

    }
}
