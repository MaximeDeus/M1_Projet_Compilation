/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package okalm.ocamlcompiler.java.asml;

/**
 *
 * @author defoursr
 */
public class Eq extends Exp_asml{
    public final Exp_asml e1;
    public final Exp_asml e2;
    
    public Eq(Exp_asml e1,Exp_asml e2){
        this.e1 = e1;
        this.e2 = e2;
    }

    @Override
    public void accept(AsmlVisitor v) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <E> E accept(AsmlObjVisitor<E> v) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
