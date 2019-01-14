package okalm.ast;

import okalm.tools.Id;
import okalm.type.Type;

import java.util.List;

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
