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
public class ParenExp extends Exp_asml {

    public final Exp_asml e;

    public ParenExp(Exp_asml e) {
        this.e = e;
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
