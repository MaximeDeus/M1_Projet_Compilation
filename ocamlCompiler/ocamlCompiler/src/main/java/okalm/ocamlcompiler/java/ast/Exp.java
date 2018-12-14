package okalm.ocamlcompiler.java.ast;

import java.util.Map;
import okalm.ocamlcompiler.java.*;
import okalm.ocamlcompiler.java.type.Type;

public abstract class Exp {
    public abstract void accept(Visitor v);

    public abstract <E> E accept(ObjVisitor<E> v);
    
    public abstract <E> E accept(ObjErrorVisitor<E> v) throws Exception;
    
}
