package okalm.asml;

import okalm.tools.AsmlErrorVisitor;
import okalm.tools.AsmlObjVisitor;
import okalm.tools.AsmlVisitor;

/**
 *
 * @author liakopog
 */
public interface Visitable {

    public void accept(AsmlVisitor v);

    public <E> E accept(AsmlObjVisitor<E> v);

    public <E> E accept(AsmlErrorVisitor<E> v) throws Exception;

}
