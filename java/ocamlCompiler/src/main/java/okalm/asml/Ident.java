package okalm.asml;

/**
 *
 * @author liakopog
 */
public class Ident extends IdentOrImm {

    public String ident;
    public String actionAllocateur;
    public int registre = -1; //Le registre ou la variable est sauvegardée
    public String[] memAdr;

    public Ident(String ident) {
            this.ident = ident;
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
        return v.visit(this);
    }

    @Override
    public String toString() {
        return ident;

    }
}
