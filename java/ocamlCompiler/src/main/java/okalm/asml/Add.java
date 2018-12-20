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
public class Add extends Exp_asml {

    public final Exp_asml ident;
    public final Exp_asml ioi;

    public Add(Exp_asml ident, Exp_asml ioi) {
        this.ident = ident;
        this.ioi = ioi;
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
