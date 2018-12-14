package asml;

/**
 *
 * @author liakopog
 */
public class Call extends Exp_asml {

    Label label;
    Fargs fargs;

    @Override
    public void accept(VisitorAsml v) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <E> E accept(ObjVisitorAsml<E> v) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
