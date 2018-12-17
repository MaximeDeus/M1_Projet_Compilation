package okalm.ocamlcompiler.java;

import okalm.ocamlcompiler.java.ast.*;
import okalm.ocamlcompiler.java.ast.Float;

public class ReductionLetExpressionVisitor implements ObjVisitor<Exp> {
    @Override
    public Exp visit(Unit e) {
        return null;
    }

    @Override
    public Exp visit(Bool e) {
        return null;
    }

    @Override
    public Exp visit(Int e) {
        return null;
    }

    @Override
    public Exp visit(Float e) {
        return null;
    }

    @Override
    public Exp visit(Not e) {
        return null;
    }

    @Override
    public Exp visit(Neg e) {
        return null;
    }

    @Override
    public Exp visit(Add e) {
        return null;
    }

    @Override
    public Exp visit(Sub e) {
        return null;
    }

    @Override
    public Exp visit(FNeg e) {
        return null;
    }

    @Override
    public Exp visit(FAdd e) {
        return null;
    }

    @Override
    public Exp visit(FSub e) {
        return null;
    }

    @Override
    public Exp visit(FMul e) {
        return null;
    }

    @Override
    public Exp visit(FDiv e) {
        return null;
    }

    @Override
    public Exp visit(Eq e) {
        return null;
    }

    @Override
    public Exp visit(LE e) {
        return null;
    }

    @Override
    public Exp visit(If e) {
        return null;
    }

    @Override
    public Exp visit(Let e) {
        return null;
    }

    @Override
    public Exp visit(Var e) {
        return null;
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
