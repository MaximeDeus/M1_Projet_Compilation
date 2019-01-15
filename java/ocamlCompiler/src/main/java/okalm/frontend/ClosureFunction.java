package okalm.frontend;

import okalm.ast.Exp;
import okalm.tools.PrintVisitor;
import okalm.typechecking.VarVisitor;

import java.util.Set;

/**
 * @author defoursr
 */
public class ClosureFunction {
    private String label;
    private Set<String> parameters;
    private Set<String> freeVar;
    private Exp code;

    public ClosureFunction(String label, Set<String> parameters, Exp code) {
        this.setLabel(label);
        this.parameters = parameters;
        this.code = code;
        this.freeVar = generateFreeVar(parameters, code);
    }

    /**
     * @param parameters
     * @param code
     * @return
     */
    private Set<String> generateFreeVar(Set<String> parameters, Exp code) {
        VarVisitor vv = new VarVisitor();
        Set<String> cpy = code.accept(vv);
        cpy.removeAll(parameters);
        return cpy;
    }

    @Override
    public String toString() {
        String s = "";
        System.out.println("label: " + label + "\nparameters: " + parameters.toString() + "\n free variables:" + freeVar.toString() + "\ncode:");
        PrintVisitor p = new PrintVisitor();
        code.accept(p);
        System.out.println("\n");
        return s;
    }

    /**
     * @return the Label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @return the parameters
     */
    public Set<String> getParameters() {
        return parameters;
    }

    /**
     * @return the code
     */
    public Exp getCode() {
        return code;
    }

    /**
     * @param label nom du label (avec ou sans '_', ajouté automatiquement si manquant)
     */
    public final void setLabel(String label) {
        if (label.charAt(0) != '_') {
            this.label = "_" + label;
        } else {
            this.label = label;
        }
    }

    /**
     * @param parameters the parameters to set
     */
    public void setParameters(Set<String> parameters) {
        this.parameters = parameters;
    }

    /**
     * @param code the code to set
     */
    public void setCode(Exp code) {
        this.code = code;
    }

}
