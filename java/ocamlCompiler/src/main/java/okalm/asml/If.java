package okalm.asml;

/**
 *
 * @author liakopog
 */
public class If extends Exp_asml {

    public Exp_asml ifIdent;
    public Tokens token;
    public Exp_asml ioi;		//meme dans le cas de float, qui demandent un Ident seulement, on utilise un IdentOrImm pour simplifier
    public Exp_asml thenasmt, elseasmt;

    public If(Exp_asml i, Tokens t, Exp_asml ioi, Exp_asml th, Exp_asml el) throws Exception {
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
    public <E> E accept(AsmlObjVisitor<E> v) {
        return v.visit(this);
    }

    @Override
    public void accept(AsmlVisitor v) {
        v.visit(this);
    }

}
