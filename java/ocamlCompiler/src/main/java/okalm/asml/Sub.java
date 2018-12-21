package okalm.asml;

/**
 *
 * @author liakopog
 */
public class Sub extends Exp_asml {

    public final Exp_asml ident;
    public final Exp_asml ioi;

    public Sub(Exp_asml ident, Exp_asml ioi) {
        this.ident = ident;
        this.ioi = ioi;
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
