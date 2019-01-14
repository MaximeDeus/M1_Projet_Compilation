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
import java.util.List;
import java.util.Map;

/**
 * @author liakopog
 */
public class BasicAllocationVisitor implements AsmlObjVisitor<Exp_asml> {
    public String regList;
    public Map<String, Integer> reg;
    public int regNum;

    public BasicAllocationVisitor() {
        reg = new HashMap();
        regList = "";
        regNum = 4; //premier registre libre

    }

    @Override
    public Exp_asml visit(Add e) {
        e.ident = e.ident.accept(this);
        e.identOrImm = e.identOrImm.accept(this);
        return e;
    }

    @Override
    public Exp_asml visit(Sub e) {
        e.ident = e.ident.accept(this);
        e.identOrImm = e.identOrImm.accept(this);
        return e;
    }

    @Override
    public Exp_asml visit(Asmt e) {
        e.ident = e.ident.accept(this);
        e.e = e.e.accept(this);
        e.asmt = e.asmt.accept(this);
        return e;
    }

    @Override
    public Exp_asml visit(Call e) {
        List<Exp_asml> l = new ArrayList();
        if (!e.fargs.isEmpty()) {
            e.fargs.forEach((element) -> {
                l.add(element.accept(this));
            });
        }
        e.fargs = l;
        return e;
    }

    @Override
    public Exp_asml visit(CallClo e) {
        List<Exp_asml> l = new ArrayList();
        if (!e.fargs.isEmpty()) {
            e.fargs.forEach((element) -> {

                l.add(element.accept(this));
            });
        }
        e.fargs = l;
        return e;
    }

    @Override
    public Exp_asml visit(Fargs e) {
        e.ident = e.ident.accept(this);
        return e;
    }

    @Override
    public Exp_asml visit(Fundefs e) {
        e.asmt = e.asmt.accept(this);

        List<Exp_asml> fdefs = new ArrayList();
        if (!e.fundefs.isEmpty()) {
            e.fundefs.forEach((element) -> {

                fdefs.add(element.accept(this));
            });
        }
        e.fundefs = fdefs;

        List<Exp_asml> fargs = new ArrayList();
        if (!e.formal_args.isEmpty()) {
            e.formal_args.forEach((element) -> {

                fargs.add(element.accept(this));
            });
        }
        e.formal_args = fargs;
        e.ident = e.ident.accept(this);

        e.label = e.label.accept(this);
        e.ident = e.ident.accept(this);
        return e;
    }

    @Override
    public Exp_asml visit(Ident e) {
        if (!reg.containsKey(e.ident)) {
            reg.put(e.ident, regNum);
            regList += e.ident + "= R" + regNum + " | ";
            regNum++;
        }
        return new Ident("R" + reg.get(e.ident));
    }

    @Override
    public Exp_asml visit(If e) {
        e.condasmt = e.condasmt.accept(this);
        e.thenasmt = e.thenasmt.accept(this);
        e.elseasmt = e.elseasmt.accept(this);
        return e;
    }

    @Override
    public Exp_asml visit(Int e) {
        return e;
    }

    @Override
    public Exp_asml visit(Label e) {
        return e;   //Les labels restent tels quels, pas de transformation à faire
    }

    @Override
    public Exp_asml visit(Mem e) {
        e.ident1 = e.ident1.accept(this);
        e.identOrImm = e.identOrImm.accept(this);
        e.ident2 = e.ident2.accept(this);
        return e;
    }

    @Override
    public Exp_asml visit(Neg e) {
        e.ident = e.ident.accept(this);
        return e;
    }

    @Override
    public Exp_asml visit(New e) {
        e.identOrImm = e.identOrImm.accept(this);
        return e;
    }

    @Override
    public Exp_asml visit(Nop e) {
        return e;
    }

    @Override
    public Exp_asml visit(ParenExp e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Exp_asml visit(Tokens e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Exp_asml visit(Fdiv e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Exp_asml visit(Fmul e
    ) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Exp_asml visit(Fneg e
    ) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Exp_asml visit(Fsub e
    ) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Exp_asml visit(Fadd e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Exp_asml visit(Eq e) {
        e.e1 = e.e1.accept(this);
        e.e2 = e.e2.accept(this);
        return e;
    }

    @Override
    public Exp_asml visit(LE e) {
        e.e1 = e.e1.accept(this);
        e.e2 = e.e2.accept(this);
        return e;
    }
}
