/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package okalm.asml;

/**
 *
 * @author liakopog
 */
public class Fmul extends Exp_asml {

    Exp_asml ident1;
    Exp_asml ident2;

    public Fmul(Exp_asml ident1, Exp_asml ident2) {
        this.ident1 = ident1;
        this.ident2 = ident2;
    }

    @Override
    public <E> E accept(AsmlObjVisitor<E> v) {
        return v.visit(this);
    }

    @Override
    public void accept(AsmlVisitor v) {
        v.visit(this);
    }

}
