package okalm.asml;

import okalm.tools.AsmlErrorVisitor;
import okalm.tools.AsmlObjVisitor;
import okalm.tools.AsmlVisitor;

/**
 * @author liakopog
 */
public class Sub extends Exp_asml {

    public Exp_asml ident;
    public Exp_asml identOrImm;

    public Sub(Exp_asml ident, Exp_asml identOrImm) {
        this.ident = ident;
        this.identOrImm = identOrImm;
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
