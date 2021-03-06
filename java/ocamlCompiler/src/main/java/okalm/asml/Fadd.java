package okalm.asml;

import okalm.tools.AsmlErrorVisitor;
import okalm.tools.AsmlObjVisitor;
import okalm.tools.AsmlVisitor;

/**
 * @author liakopog
 */
public class Fadd extends Exp_asml {

    public final Exp_asml ident1;
    public final Exp_asml ident2;

    public Fadd(Ident ident1, Ident ident2) {
        this.ident1 = ident1;
        this.ident2 = ident2;
    }

    public Fadd(Exp_asml ident1, Exp_asml ident2) {
        this.ident1 = ident1;
        this.ident2 = ident2;
    }

    @Override
    public void accept(AsmlVisitor v) {
        v.visit(this);
    }

    @Override
    public <E> E accept(AsmlObjVisitor<E> v) {
        return v.visit(this);
    }

    @Override
    public <E> E accept(AsmlErrorVisitor<E> v) throws Exception {
        return v.visit(this);
    }

}
