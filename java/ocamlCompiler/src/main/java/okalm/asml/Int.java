package okalm.asml;

/**
 *
 * @author liakopog
 */
public class Int extends IdentOrImm {

    int e;
    
    public Int(int e){
    this.e=e;
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
