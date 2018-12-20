package okalm.asml;

/**
 *
 * @author liakopog
 */
public class If extends Exp_asml {

    public Exp_asml condasmt,thenasmt, elseasmt;

    public If(Exp_asml condasmt, Exp_asml thenasmt, Exp_asml elseasmt) {
        this.condasmt = condasmt;
        this.thenasmt = thenasmt;
        this.elseasmt = elseasmt;
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
