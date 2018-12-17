package okalm.ocamlcompiler.java.asml;

/**
 *
 * @author liakopog
 */
public interface Visitable {

    public void accept(AsmlVisitor v);

    public <E> E accept(AsmlObjVisitor<E> v);

}
