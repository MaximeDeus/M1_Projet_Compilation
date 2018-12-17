package asml;

/**
 *
 * @author liakopog
 */
public class Fneg extends Exp_asml {

    Ident ident;

    public Fneg(Ident ident) {
        this.ident = ident;
    }

    @Override
    public void accept(VisitorAsml v) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <E> E accept(ObjVisitorAsml<E> v) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
