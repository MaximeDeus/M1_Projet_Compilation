package asml;

/**
 *
 * @author liakopog
 */
public class Label extends Ident {

    //Pour le moment, l'underscore avec lequel doit commencer un label est implicite
    public Label(String i) {
        super(i); //On assume qu'apres l'underscore il faut toujours commencer avec une lettre minuscule
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
