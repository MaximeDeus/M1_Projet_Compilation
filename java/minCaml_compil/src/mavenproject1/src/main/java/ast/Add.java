/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ast;

/**
 *
 * @author diardjul
 */
public class Add extends Exp {
    final Exp e1;
    final Exp e2;

    Add(Exp e1, Exp e2) {
        this.e1 = e1;
        this.e2 = e2;
    }

    @Override
    <E> E accept(ObjVisitor<E> v) {
        return v.visit(this);
    }
    @Override
    void accept(Visitor v) {
        v.visit(this);
    }
}
