package okalm.ocamlcompiler.java.asml;

/**
 *
 * @author liakopog
 */
public class Call extends Exp_asml {

    public final Label label;
    public final Fargs fargs;

    public Call(Label label, Fargs fargs) {
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
