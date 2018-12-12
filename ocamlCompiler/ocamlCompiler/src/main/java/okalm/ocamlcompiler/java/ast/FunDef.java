package okalm.ocamlcompiler.java.ast;

import java.util.List;
import okalm.ocamlcompiler.java.*;
import okalm.ocamlcompiler.java.type.*;

public class FunDef {
    public final Id id;
    public final Type type;
    public final List<Id> args;
    public final Exp e;

    public FunDef(Id id, Type t, List<Id> args, Exp e) {
        this.id = id;
        this.type = t;
        this.args = args;
        this.e = e;
    }
 
}
