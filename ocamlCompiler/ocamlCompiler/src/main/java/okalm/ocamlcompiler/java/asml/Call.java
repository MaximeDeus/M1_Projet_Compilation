package okalm.ocamlcompiler.java.asml;

import java.util.List;

/**
 *
 * @author liakopog
 */
public class Call extends Exp_asml {

    public final Exp_asml label;
    public final List<Exp_asml> fargs;

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

}
