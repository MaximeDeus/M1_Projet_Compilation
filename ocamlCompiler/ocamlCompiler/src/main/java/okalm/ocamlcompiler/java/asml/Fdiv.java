/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package okalm.ocamlcompiler.java.asml;

/**
 *
 * @author liakopog
 */
public class Fdiv extends Exp_asml {

    public Exp_asml ident1;
    public Exp_asml ident2;

    public Fdiv(Exp_asml ident1, Exp_asml ident2) {
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
