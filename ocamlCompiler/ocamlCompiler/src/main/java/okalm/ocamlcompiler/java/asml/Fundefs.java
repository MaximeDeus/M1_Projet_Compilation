package okalm.ocamlcompiler.java.asml;

/**
 *
 * @author liakopog
 */
public class Fundefs implements Visitable {

    public Exp_asml asmt; //cas LET UNDERSC EQUAL asmt
    public Fundefs fundefs; //cas LET LABEL EQUAL FLOAT fundefs
    public Exp_asml formal_args; //cas  LET LABEL formal_args EQUAL asmt fundefs
    public Exp_asml label;
    public Exp_asml ident;    //pour representer le float, je ne sais pas pourquoi dans ce cas il n'est pas encodé par un ident comme d'habitude

    @Override
    public <E> E accept(AsmlObjVisitor<E> v) {
        return v.visit(this);
    }

    @Override
    public void accept(AsmlVisitor v) {
        v.visit(this);
    }

}
