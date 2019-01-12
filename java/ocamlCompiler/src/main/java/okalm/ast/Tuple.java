package okalm.ast;

import java.util.List;

import okalm.tools.ObjErrorVisitor;
import okalm.tools.ObjVisitor;
import okalm.tools.Visitor;

public class Tuple extends Exp {
    public final List<Exp> es;

    public Tuple(List<Exp> es) {
        this.es = es;
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
