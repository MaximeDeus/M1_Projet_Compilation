package asml;

/**
 *
 * @author liakopog
 */
public class Fundefs {

    public final Asmt asmt; //cas LET UNDERSC EQUAL asmt
    public final Fundefs fundefs; //cas LET LABEL EQUAL FLOAT fundefs
    public final Fargs formal_args; //cas  LET LABEL formal_args EQUAL asmt fundefs
    public final Label label;
    public final Ident ident;    //pour representer le float, je ne sais pas pourquoi dans ce cas il n'est pas encodé par un ident comme d'habitude

    public Fundefs(Asmt asmt) {
        this.asmt = asmt;
        this.fundefs = null;
        this.formal_args = null;
        this.label = null;
        this.ident = null;

    }

    public Fundefs(Fundefs fundefs, Label label, Ident ident) {
        this.asmt = null;
        this.fundefs = fundefs;
        this.formal_args = null;
        this.label = label;
        this.ident = ident;
    }

    public Fundefs(Asmt asmt, Fundefs fundefs, Fargs formal_args, Label label, Ident ident) {
        this.asmt = asmt;
        this.fundefs = fundefs;
        this.formal_args = formal_args;
        this.label = label;
        this.ident = ident;
    }

}
