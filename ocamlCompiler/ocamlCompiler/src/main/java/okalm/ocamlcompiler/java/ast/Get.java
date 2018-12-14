package okalm.ocamlcompiler.java.ast;

import okalm.ocamlcompiler.java.*;

public class Get extends Exp {
    public final Exp e1;
    public final Exp e2;

    public Get(Exp e1, Exp e2) {
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
