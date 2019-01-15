package okalm.ast;

import okalm.tools.ObjErrorVisitor;
import okalm.tools.ObjVisitor;
import okalm.tools.Visitor;

public class Unit extends Exp {

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
