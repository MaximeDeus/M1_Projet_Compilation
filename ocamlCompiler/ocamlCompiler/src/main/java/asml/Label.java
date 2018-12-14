package asml;

/**
 *
 * @author liakopog
 */
public class Label extends Exp_asml {

    //TODO:Un Label doit toujours commencer avec un underscore (_)
    Fargs formal_args;
    Asmt asmt;
    String label;

    @Override
    public void accept(VisitorAsml v) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <E> E accept(ObjVisitorAsml<E> v) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
