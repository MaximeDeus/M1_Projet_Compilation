package okalm.ocamlcompiler.java.ast;

import okalm.ocamlcompiler.java.Id;
import okalm.ocamlcompiler.java.type.*;
import okalm.ocamlcompiler.java.*;

public class Let extends Exp {
    public final Id id;
    public final Type t;
    public final Exp e1;
    public final Exp e2;

    public Let(Id id, Type t, Exp e1, Exp e2) {
        this.id = id;
        this.t = t;
        this.e1 = e1;
        this.e2 = e2;
    }

    public <E> E accept(ObjVisitor<E> v) {
        return v.visit(this);
    }
    public void accept(Visitor v) {
        v.visit(this);
    }
}
