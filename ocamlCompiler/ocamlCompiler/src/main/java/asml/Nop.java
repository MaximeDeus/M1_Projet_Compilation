package asml;

/**
 *
 * @author liakopog
 */
public class Nop extends Exp_asml {

    public final String e = "nop";

    @Override
    public void accept(VisitorAsml v) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <E> E accept(ObjVisitorAsml<E> v) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
