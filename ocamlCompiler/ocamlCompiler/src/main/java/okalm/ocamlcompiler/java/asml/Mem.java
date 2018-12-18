package okalm.ocamlcompiler.java.asml;

/**
 *
 * @author liakopog
 */
public class Mem extends Exp_asml {

    public final Exp_asml ident1;
    public final Exp_asml ioi;
    public final Exp_asml ident2;

    public Mem(Exp_asml ident1, Exp_asml ioi, Exp_asml ident2) {
        this.ident1 = ident1;
        this.ioi = ioi;
        this.ident2 = ident2;
    }

    public Mem(Exp_asml ident1, Exp_asml ioi) {
        this.ident1 = ident1;
        this.ioi = ioi;
        this.ident2 = null;
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
