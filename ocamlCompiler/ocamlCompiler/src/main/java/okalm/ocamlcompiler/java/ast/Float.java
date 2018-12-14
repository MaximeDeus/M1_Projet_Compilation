package okalm.ocamlcompiler.java.ast;

import okalm.ocamlcompiler.java.*;

public class Float extends Exp {
    public float f;

    public Float(float f) {
        this.f = f;
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
