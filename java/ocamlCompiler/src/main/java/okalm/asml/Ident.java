package okalm.asml;

/**
 *
 * @author liakopog
 */
public class Ident extends IdentOrImm {

    public String ident;

    public Ident(String ident) {
        //if (Character.isLowerCase(ident.charAt(0))) {
            this.ident = ident;
        //}

    }

    @Override
    public <E> E accept(AsmlObjVisitor<E> v) {
        return v.visit(this);
    }

    @Override
    public void accept(AsmlVisitor v) {
        v.visit(this);
    }

}
