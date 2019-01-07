package okalm.asml;

import java.util.List;

/**
 *
 * @author liakopog
 */
public class Call extends Exp_asml {

    public Exp_asml label;
    public List<Exp_asml> fargs;

    public Call(Label label, List<Exp_asml> fargs) {
        this.label = label;
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
