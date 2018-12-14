package asml;

/**
 *
 * @author liakopog
 */
class Fargs implements Visitable {

    Ident ident;    //case IDENT
    Fargs fargs;    //case IDENT formal_args
    Boolean NIL;    //case NIL

    @Override
    public void accept(VisitorAsml v) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <E> E accept(ObjVisitorAsml<E> v) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
