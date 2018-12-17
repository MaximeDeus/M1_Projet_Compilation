package asml;

/**
 *
 * @author liakopog
 */
public class Sub extends Exp_asml {

    public final Ident ident;
    public final IdentOrImm ioi;

    public Sub(Ident ident, IdentOrImm ioi) {
        this.ident = ident;
        this.ioi = ioi;
    }

    @Override
    public void accept(AsmlVisitor v) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <E> E accept(AsmlObjVisitor<E> v) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
