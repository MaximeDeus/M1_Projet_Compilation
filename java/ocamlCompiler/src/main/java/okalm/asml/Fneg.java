package okalm.asml;

/**
 *
 * @author liakopog
 */
public class Fneg extends Exp_asml {

    Exp_asml ident;

    public Fneg(Exp_asml ident) {
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}