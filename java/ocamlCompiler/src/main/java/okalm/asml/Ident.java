package okalm.asml;

/**
 *
 * @author liakopog
 */
public class Ident extends IdentOrImm {

    String ident;
    String actionAllocateur;
    int registre = -1; //Le registre ou la variable est sauvegardée
    String[] memAdr;

    public Ident(String ident) {
        if (Character.isLowerCase(ident.charAt(0))) {
            this.ident = ident;
        }

    }

    @Override
    public <E> E accept(AsmlObjVisitor<E> v) {
        return v.visit(this);
    }

    @Override
    public void accept(AsmlVisitor v) {
        v.visit(this);
    }

    public Exp_asml getActionAllocateur() {
        return actionAllocateur;
    }

    public void setActionAllocateur(Exp_asml actionAllocateur) {
        this.actionAllocateur = actionAllocateur;
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
