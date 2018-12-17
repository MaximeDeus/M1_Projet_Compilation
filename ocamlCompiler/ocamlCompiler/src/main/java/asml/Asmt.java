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
		// TODO Auto-generated method stub
		
	}
	@Override
	public <E> E accept(ObjVisitorAsml<E> v) {
		// TODO Auto-generated method stub
		return null;
	}



}
