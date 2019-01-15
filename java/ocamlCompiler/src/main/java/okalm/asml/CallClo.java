package okalm.asml;

import okalm.tools.AsmlErrorVisitor;
import okalm.tools.AsmlObjVisitor;
import okalm.tools.AsmlVisitor;

import java.util.List;

/**
 * @author liakopog
 */
public class CallClo extends Exp_asml {

    public Exp_asml ident;
    public List<Exp_asml> fargs;

    public CallClo(Ident ident, List<Exp_asml> fargs) {
        this.ident = ident;
        this.fargs = fargs;
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
