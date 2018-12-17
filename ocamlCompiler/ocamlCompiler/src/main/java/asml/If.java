package asml;

/**
 *
 * @author liakopog
 */
public class If extends Exp_asml {

    public Ident ifIdent;
    public Tokens token;
    public IdentOrImm ioi;		//meme dans le cas de float, qui demandent un Ident seulement, on utilise un IdentOrImm pour simplifier
    public Asmt thenasmt, elseasmt;

    public If(Ident i, Tokens t, IdentOrImm ioi, Asmt th, Asmt el) throws Exception {
        switch (t) {
            case FEQUAL:
            case FLE:
                if (!(ioi instanceof Ident)) {
                    assert (false);
                    throw new Exception("type error, if statement has float operator but non float operand");

                }
        }
    }

    @Override
    public void accept(VisitorAsml v
    ) {
        // TODO Auto-generated method stub

    }

    @Override
    public <E> E accept(ObjVisitorAsml< E> v) {
        // TODO Auto-generated method stub
        return null;
    }

}
