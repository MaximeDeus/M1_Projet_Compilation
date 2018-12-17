package asml;

/**
 *
 * @author liakopog
 */
public abstract class ObjVisitorAsml<E> {

    public abstract <E> E visit(Visitable ac);
}
