package okalm.ast;

import okalm.Id;
import okalm.ObjErrorVisitor;
import okalm.ObjVisitor;
import okalm.Visitor;

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
