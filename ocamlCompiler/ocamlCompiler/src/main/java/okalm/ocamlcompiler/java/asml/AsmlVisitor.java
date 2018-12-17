package okalm.ocamlcompiler.java.asml;

/**
 *
 * @author liakopog
 */
public interface AsmlVisitor {

    void visit(Add e);

    void visit(Sub e);

    void visit(Asmt e);

    void visit(Call e);

    void visit(CallClo e);

    void visit(Fadd e);

    void visit(Fargs e);

    void visit(Fdiv e);

    void visit(Fmul e);

    void visit(Fneg e);

    void visit(Fsub e);

    void visit(Fundefs e);

    void visit(Ident e);

    void visit(If e);

    void visit(Int e);

    void visit(Label e);

    void visit(Mem e);

    void visit(Neg e);

    void visit(New e);

    void visit(Nop e);

    void visit(ParenExp e);

    void visit(Tokens e);
}
