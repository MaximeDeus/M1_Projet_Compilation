package asml;

/**
 *
 * @author liakopog
 */
public class Mem extends Exp_asml {

    public final Ident ident1;
    public final IdentOrImm ioi;
    public final Ident ident2;

    public Mem(Ident ident1, IdentOrImm ioi, Ident ident2) {
        this.ident1 = ident1;
        this.ioi = ioi;
        this.ident2 = ident2;
    }

    public Mem(Ident ident1, IdentOrImm ioi) {
        this.ident1 = ident1;
        this.ioi = ioi;
        this.ident2 = null;
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
