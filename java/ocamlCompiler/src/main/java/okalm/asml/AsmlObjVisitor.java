package okalm.asml;

/**
 *
 * @author liakopog
 */
public interface AsmlObjVisitor<E> {

    public <E> E visit(Add e);

    public <E> E visit(Sub e);

    public <E> E visit(Asmt e);

    public <E> E visit(Call e);

    public <E> E visit(CallClo e);

    public <E> E visit(Fadd e);

    public <E> E visit(Fargs e);

    public <E> E visit(Fdiv e);

    public <E> E visit(Fmul e);

    public <E> E visit(Fneg e);

    public <E> E visit(Fsub e);

    public <E> E visit(Fundefs e);

    public <E> E visit(Ident e);

    public <E> E visit(If e);

    public <E> E visit(Int e);

    public <E> E visit(Label e);

    public <E> E visit(Mem e);

    public <E> E visit(Neg e);

    public <E> E visit(New e);

    public <E> E visit(Nop e);

    public <E> E visit(ParenExp e);

    public <E> E visit(Tokens e);

}
