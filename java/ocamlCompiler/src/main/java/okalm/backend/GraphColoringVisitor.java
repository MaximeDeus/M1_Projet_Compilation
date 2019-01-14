/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package okalm.backend;

import okalm.asml.*;
import okalm.tools.AsmlObjVisitor;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author liakopog
 */
public class GraphColoringVisitor implements AsmlObjVisitor<Object> {

    HashMap<Exp_asml, ArrayList<String>> listeVarActives;
    Graphe graphe;

    public GraphColoringVisitor(HashMap<Exp_asml, ArrayList<String>> listeVA) {
        listeVarActives = listeVA;
        graphe = new Graphe();
    }

    @Override
    public Object visit(Add e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object visit(Sub e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object visit(Asmt e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object visit(Eq e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object visit(LE e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object visit(Call e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object visit(CallClo e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object visit(Fadd e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object visit(Fargs e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object visit(Fdiv e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object visit(Fmul e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object visit(Fneg e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object visit(Fsub e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object visit(Fundefs e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object visit(Ident e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object visit(If e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object visit(Int e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object visit(Label e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object visit(Mem e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object visit(Neg e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object visit(New e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object visit(Nop e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object visit(ParenExp e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object visit(Tokens e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
