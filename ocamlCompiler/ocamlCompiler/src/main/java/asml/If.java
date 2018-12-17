package asml;

/**
 *
 * @author liakopog
 */
public class If extends Exp_asml{
	Ident ifIdent;
	Tokens token;
	IdentOrImm ioi;		//meme dans le cas de float, qui demandent un Ident seulement, on utilise un IdentOrImm pour simplifier
	Asmt thenasmt,elseasmt;
	
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
