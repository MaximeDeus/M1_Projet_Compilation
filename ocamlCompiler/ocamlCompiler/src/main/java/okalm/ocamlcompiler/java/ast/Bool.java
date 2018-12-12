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
public class Bool extends Exp {
    final boolean b;

    Bool(boolean b) {
        this.b = b;
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