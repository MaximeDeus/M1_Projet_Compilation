package okalm.ocamlcompiler.java.ast;

import okalm.ocamlcompiler.java.*;

public abstract class Exp {
    public abstract void accept(Visitor v);

    public abstract <E> E accept(ObjVisitor<E> v);
}
