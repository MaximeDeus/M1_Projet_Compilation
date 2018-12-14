package okalm.ocamlcompiler.java.ast;

import okalm.ocamlcompiler.java.Id;
import okalm.ocamlcompiler.java.Visitor;
import okalm.ocamlcompiler.java.*;

public class Var extends Exp {
    public final Id id;

    public Var(Id id) {
        this.id = id;
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
