package okalm.asml;

/**
 *
 * @author liakopog
 */
public class Nop extends Exp_asml {

    public final String e = "nop";

    @Override
    public <E> E accept(AsmlObjVisitor<E> v) {
        return v.visit(this);
    }

    @Override
    public void accept(AsmlVisitor v) {
        v.visit(this);
    }

}
