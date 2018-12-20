package okalm.asml;

/**
 *
 * @author liakopog
 */
public interface AsmlErrorVisitor<E> {

    public <E> E visit(Add e) throws Exception;

    public <E> E visit(Sub e) throws Exception;

    public <E> E visit(Asmt e) throws Exception;

    public <E> E visit(Call e) throws Exception;

    public <E> E visit(CallClo e) throws Exception;

    public <E> E visit(Fadd e) throws Exception;

    public <E> E visit(Fargs e) throws Exception;

    public <E> E visit(Fdiv e) throws Exception;

    public <E> E visit(Fmul e) throws Exception;

    public <E> E visit(Fneg e) throws Exception;

    public <E> E visit(Fsub e) throws Exception;

    public <E> E visit(Fundefs e) throws Exception;

    public <E> E visit(Ident e) throws Exception;

    public <E> E visit(If e) throws Exception;

    public <E> E visit(Int e) throws Exception;

    public <E> E visit(Label e) throws Exception;

    public <E> E visit(Mem e) throws Exception;

    public <E> E visit(Neg e) throws Exception;

    public <E> E visit(New e) throws Exception;

    public <E> E visit(Nop e) throws Exception;

    public <E> E visit(ParenExp e) throws Exception;

    public <E> E visit(Tokens e) throws Exception;
}
