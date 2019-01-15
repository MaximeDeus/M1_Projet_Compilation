package okalm.tools;

import okalm.asml.*;

/**
 * @author liakopog
 */
public interface AsmlObjVisitor<E> {

    public E visit(Add e);

    public E visit(Sub e);

    public E visit(Asmt e);

    public E visit(Eq e);

    public E visit(LE e);

    public E visit(Call e);

    public E visit(CallClo e);

    public E visit(Fadd e);

    public E visit(Fargs e);

    public E visit(Fdiv e);

    public E visit(Fmul e);

    public E visit(Fneg e);

    public E visit(Fsub e);

    public E visit(Fundefs e);

    public E visit(Ident e);

    public E visit(If e);

    public E visit(Int e);

    public E visit(Label e);

    public E visit(Mem e);

    public E visit(Neg e);

    public E visit(New e);

    public E visit(Nop e);

    public E visit(ParenExp e);

    public E visit(Tokens e);

}
