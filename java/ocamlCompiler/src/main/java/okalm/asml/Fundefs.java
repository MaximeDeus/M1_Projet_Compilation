package okalm.asml;

import java.util.List;

/**
 *
 * @author liakopog
 */
public class Fundefs extends Exp_asml {

    public final Exp_asml asmt; //cas LET UNDERSC EQUAL asmt
    public final List<Fundefs> fundefs; //cas LET LABEL EQUAL FLOAT fundefs
    public final List<Exp_asml> formal_args; //cas  LET LABEL formal_args EQUAL asmt fundefs
    public final Exp_asml label;
    public final Exp_asml ident;    //pour representer le float, je ne sais pas pourquoi dans ce cas il n'est pas encodé par un ident comme d'habitude

    public Fundefs(Asmt asmt) {
        this.asmt = asmt;
        this.fundefs = null;
        this.formal_args = null;
        this.label = null;
        this.ident = null;

    }

    public Fundefs(List<Fundefs> fundefs, Label label, Ident ident) {
        this.asmt = null;
        this.fundefs = fundefs;
        this.formal_args = null;
        this.label = label;
        this.ident = ident;
    }

    public Fundefs(Exp_asml asmt, List<Fundefs> fundefs, List<Exp_asml> formal_args, Exp_asml label, Exp_asml ident) {
        this.asmt = asmt;
        this.fundefs = fundefs;
        this.formal_args = formal_args;
        this.label = label;
        this.ident = ident;
    }

    @Override
    public <E> E accept(AsmlObjVisitor<E> v) {
        return v.visit(this);
    }

    @Override
    public void accept(AsmlVisitor v) {
        v.visit(this);
    }

    @Override
    public <E> E accept(AsmlErrorVisitor<E> v) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
