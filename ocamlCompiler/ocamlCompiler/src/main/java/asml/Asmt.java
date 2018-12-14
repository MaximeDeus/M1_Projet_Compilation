package asml;

/**
 *
 * @author liakopog
 */
class Asmt implements Visitable {

    Exp_asml e;     //exp
    Boolean paren;  //LPAREN asmt RPAREN
    Asmt asmt;      //LET IDENT EQUAL exp IN asmt
    Ident ident;    //LET IDENT EQUAL exp IN asmt

    @Override
    public void accept(VisitorAsml v) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <E> E accept(ObjVisitorAsml<E> v) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
