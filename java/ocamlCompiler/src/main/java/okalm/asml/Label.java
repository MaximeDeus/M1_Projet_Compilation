package okalm.asml;

/**
 *
 * @author liakopog
 */
public class Label extends Ident {

    //Pour le moment, l'underscore avec lequel doit commencer un label est implicite
    public Label(String i) {
        super(i);
    }

    @Override
    public <E> E accept(AsmlObjVisitor<E> v) {
        return v.visit(this);
    }

    @Override
    public void accept(AsmlVisitor v) {
        v.visit(this);
    }
}
