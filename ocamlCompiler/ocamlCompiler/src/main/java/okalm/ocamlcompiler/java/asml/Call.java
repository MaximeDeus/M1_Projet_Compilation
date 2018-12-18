package okalm.ocamlcompiler.java.asml;

/**
 *
 * @author liakopog
 */
public class Call extends Exp_asml {

    public final Exp_asml label;
    public final Exp_asml fargs;

    public Call(Exp_asml label, Exp_asml fargs) {
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
