package okalm.ocamlcompiler.java.asml;

/**
 *
 * @author liakopog
 */
public class Int extends IdentOrImm {

    int e;

    @Override
    public <E> E accept(AsmlObjVisitor<E> v) {
        return v.visit(this);
    }

    @Override
    public void accept(AsmlVisitor v) {
        v.visit(this);
    }

}
