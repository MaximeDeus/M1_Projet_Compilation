package okalm.ocamlcompiler.java;

import okalm.ocamlcompiler.java.ast.*;
import okalm.ocamlcompiler.java.ast.Float;
import okalm.ocamlcompiler.java.type.Type;

public class ReductionLetExpressionVisitor implements ObjVisitor<Exp> {
    @Override
    public Exp visit(Unit e) {
        return e;
    }

    @Override
    public Exp visit(Bool e) {
        return e;
    }

    @Override
    public Exp visit(Int e) {
        return e;
    }

    @Override
    public Exp visit(Float e) {
        return e;
    }

    @Override
    public Exp visit(Not e) {
        return e;
    }

    @Override
    public Exp visit(Neg e) {
        return e;
    }

    @Override
    public Exp visit(Add e) {
        return e;
    }

    @Override
    public Exp visit(Sub e) {
        return e;
    }

    @Override
    public Exp visit(FNeg e) {
        return e;
    }

    @Override
    public Exp visit(FAdd e) {
        return e;
    }

    @Override
    public Exp visit(FSub e) {
        return e;
    }

    @Override
    public Exp visit(FMul e) {
        return e;
    }

    @Override
    public Exp visit(FDiv e) {
        return e;
    }

    @Override
    public Exp visit(Eq e) {
        return e;
    }

    @Override
    public Exp visit(LE e) {
        return e;
    }

    @Override
    public Exp visit(If e) {
        return e;
    }

    @Override
    public Exp visit(Let e) {
        Exp e1 = e.e1.accept(this);
        Exp e2 = e.e2.accept(this);
        if (e.e1 instanceof Let){
            Let e1_bis = (Let) e1;
            Let res = new Let(e1_bis.id, e1_bis.t, e1,
                    new Let(e.id, e.t, e1_bis.e2, e2));
            return res;
        }
        return e;
    }

    @Override
    public Exp visit(Var e) {
        return e;
    }

    @Override
    public Exp visit(LetRec e) {
        return null;
    }

    @Override
    public Exp visit(App e) {
        return null;
    }

    @Override
    public Exp visit(Tuple e) {
        return null;
    }

    @Override
    public Exp visit(LetTuple e) {
        return null;
    }

    @Override
    public Exp visit(Array e) {
        return null;
    }

    @Override
    public Exp visit(Get e) {
        return null;
    }

    @Override
    public Exp visit(Put e) {
        return null;
    }
}
