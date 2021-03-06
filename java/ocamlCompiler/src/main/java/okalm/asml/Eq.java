package okalm.asml;

import okalm.tools.AsmlErrorVisitor;
import okalm.tools.AsmlObjVisitor;
import okalm.tools.AsmlVisitor;

/**
 * @author defoursr
 */
public class Eq extends Exp_asml {

    public Exp_asml e1;
    public Exp_asml e2;

    public Eq(Exp_asml e1, Exp_asml e2) {
        this.e1 = e1;
        this.e2 = e2;
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
