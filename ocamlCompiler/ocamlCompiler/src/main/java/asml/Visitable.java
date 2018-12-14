package asml;

/**
 *
 * @author liakopog
 */
public interface Visitable {

    public abstract void accept(VisitorAsml v);

    public abstract <E> E accept(ObjVisitorAsml<E> v);

}
