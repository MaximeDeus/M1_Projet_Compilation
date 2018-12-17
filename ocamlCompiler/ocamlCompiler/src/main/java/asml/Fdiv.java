/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asml;

/**
 *
 * @author liakopog
 */
public class Fdiv extends Exp_asml {

    public Ident ident1;
    public Ident ident2;

    public Fdiv(Ident ident1, Ident ident2) {
        this.ident1 = ident1;
        this.ident2 = ident2;
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
