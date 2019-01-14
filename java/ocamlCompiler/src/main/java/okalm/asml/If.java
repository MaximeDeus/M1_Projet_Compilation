package okalm.asml;

import okalm.tools.AsmlErrorVisitor;
import okalm.tools.AsmlObjVisitor;
import okalm.tools.AsmlVisitor;

/**
 * @author liakopog
 */
public class If extends Exp_asml {

    public Exp_asml condasmt, thenasmt, elseasmt;

    public If(Exp_asml condasmt, Exp_asml thenasmt, Exp_asml elseasmt) {
        this.condasmt = condasmt;
        this.thenasmt = thenasmt;
        this.elseasmt = elseasmt;
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

}
