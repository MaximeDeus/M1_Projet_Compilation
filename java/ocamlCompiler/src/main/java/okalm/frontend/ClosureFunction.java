/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package okalm.frontend;

import java.util.List;

import okalm.tools.PrintVisitor;
import okalm.ast.Exp;

/**
 *
 * @author defoursr
 */
public class ClosureFunction {
    private String label;
    private List<String> parameters;
    private Exp code;

    public ClosureFunction(String label, List<String> parameters, Exp code) {
        this.setLabel(label);
        this.parameters = parameters;
        this.code = code;
    }
    
    @Override
    public String toString(){
        String s ="";
        System.out.print(label + ", " + parameters.toString()+"=\n");
        PrintVisitor p = new PrintVisitor();
        code.accept(p);
        System.out.print("\n");
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
    public List<String> getParameters() {
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
    public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }

    /**
     * @param code the code to set
     */
    public void setCode(Exp code) {
        this.code = code;
    }

}
