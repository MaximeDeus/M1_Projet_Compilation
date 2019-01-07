package okalm.ast;

import okalm.ObjErrorVisitor;
import okalm.ObjVisitor;
import okalm.Visitor;

public class Bool extends Exp {
    public final boolean b;

    public Bool(boolean b) {
        this.b = b;
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