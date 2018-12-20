package okalm.ast;

import okalm.ObjErrorVisitor;
import okalm.ObjVisitor;
import okalm.Visitor;

public class Int extends Exp {
    public final int i;

    public Int(int i) {
        this.i = i;
    }


    public <E> E accept(ObjVisitor<E> v) {
        return v.visit(this);
    }

    public void accept(Visitor v) {
        v.visit(this);
    }    
    public <E> E accept(ObjErrorVisitor<E> v) throws Exception {
        return v.visit(this);
    }
    
}
