package okalm.ast;

import okalm.tools.ObjErrorVisitor;
import okalm.tools.ObjVisitor;
import okalm.tools.Visitor;

public class Add extends Exp {
    public final Exp e1;
    public final Exp e2;

    public Add(Exp e1, Exp e2) {
        this.e1 = e1;
        this.e2 = e2;
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
