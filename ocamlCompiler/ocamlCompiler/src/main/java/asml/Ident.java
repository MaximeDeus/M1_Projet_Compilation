package asml;

/**
 *
 * @author liakopog
 */
public class Ident extends Exp_asml implements IdentOrImm {

    //TODO:s'assurer que le premier caractere est une lettre minuscule
    String ident;

    @Override
    public void accept(VisitorAsml v) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <E> E accept(ObjVisitorAsml<E> v) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
