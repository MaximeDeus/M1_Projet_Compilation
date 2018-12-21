package okalm.asml;

/**
 *
 * @author liakopog
 */
public class Int extends IdentOrImm {

    public int e;

    public Int(int e) {
        this.e = e;
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
