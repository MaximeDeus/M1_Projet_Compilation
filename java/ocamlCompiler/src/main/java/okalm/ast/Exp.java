package okalm.ast;

import okalm.tools.ObjErrorVisitor;
import okalm.tools.ObjVisitor;
import okalm.tools.Visitor;

public abstract class Exp {
    public abstract void accept(Visitor v);

    public abstract <E> E accept(ObjVisitor<E> v);

    public abstract <E> E accept(ObjErrorVisitor<E> v) throws Exception;

}
