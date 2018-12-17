package okalm.ocamlcompiler.java.asml;

/**
 *
 * @author liakopog
 */
public class Ident extends IdentOrImm {

    String ident;

    public Ident(String ident) {
        if (Character.isLowerCase(ident.charAt(0))) {
            this.ident = ident;
        }

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
