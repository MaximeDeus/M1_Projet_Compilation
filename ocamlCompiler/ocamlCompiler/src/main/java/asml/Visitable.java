package asml;

/**
 *
 * @author liakopog
 */
public interface Visitable {

    public void accept(VisitorAsml v);

    public <E> E accept(ObjVisitorAsml<E> v);

}
