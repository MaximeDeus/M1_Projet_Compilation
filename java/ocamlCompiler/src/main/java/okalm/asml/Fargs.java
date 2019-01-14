package okalm.asml;

import okalm.tools.AsmlErrorVisitor;
import okalm.tools.AsmlObjVisitor;
import okalm.tools.AsmlVisitor;

/**
 * @author liakopog
 */
public class Fargs extends Exp_asml {

    public Exp_asml ident;    //case IDENT
    public Boolean estNIL;    //case NIL TODO:a effacer

    public Fargs(Boolean estNIL) {
        this.estNIL = estNIL;
        this.ident = null;
    }

    public Fargs(Exp_asml ident) {
        this.ident = ident;
        this.estNIL = false;

    }

    @Override
    public void accept(AsmlVisitor v) {
        v.visit(this);
    }

    @Override
    public <E> E accept(AsmlObjVisitor<E> v) {
        return v.visit(this);
    }

    @Override
    public <E> E accept(AsmlErrorVisitor<E> v) throws Exception {
        return v.visit(this);
    }

}
