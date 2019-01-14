package okalm.tools;

import okalm.asml.*;

/**
 * @author liakopog
 */
public interface AsmlErrorVisitor<E> {

    public E visit(Add e) throws Exception;

    public E visit(Sub e) throws Exception;

    public E visit(Asmt e) throws Exception;

    public E visit(Eq e) throws Exception;

    public E visit(LE e) throws Exception;

    public E visit(Call e) throws Exception;

    public E visit(CallClo e) throws Exception;

    public E visit(Fadd e) throws Exception;

    public E visit(Fargs e) throws Exception;

    public E visit(Fdiv e) throws Exception;

    public E visit(Fmul e) throws Exception;

    public E visit(Fneg e) throws Exception;

    public E visit(Fsub e) throws Exception;

    public E visit(Fundefs e) throws Exception;

    public E visit(Ident e) throws Exception;

    public E visit(If e) throws Exception;

    public E visit(Int e) throws Exception;

    public E visit(Label e) throws Exception;

    public E visit(Mem e) throws Exception;

    public E visit(Neg e) throws Exception;

    public E visit(New e) throws Exception;

    public E visit(Nop e) throws Exception;

    public E visit(ParenExp e) throws Exception;

    public E visit(Tokens e) throws Exception;
}
