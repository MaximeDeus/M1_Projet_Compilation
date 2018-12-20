package okalm.ocamlcompiler.java.asml;

import java.util.ArrayList;

/**
 *
 * @author liakopog
 */
public class AllSpillAllocationVisitor implements AsmlVisitor {

    int nbVarFrame = 0; //Nombre de variables dans ce frame
    Ident[] registres = new Ident[13]; //Liste representant les registres r0-r12 utilisables par le programme
    Ident[] registresSpeciales = new Ident[3];
    ArrayList<Exp_asml> mem = new ArrayList<>(); //Liste des variables allouees dans la memoire

    @Override
    public void visit(Add e) {
        e.ident.accept(this);
        e.ioi.accept(this);
    }

    @Override
    public void visit(Sub e) {
        e.ident.accept(this);
        e.ioi.accept(this);
    }

    @Override
    public void visit(Asmt e) {
        if (e.ident != null) { //Allocation de registe ou d'emplacement memoire pour variable
            e.ident.accept(this);
            //            new String("[" + "fp+" + (Integer.toString(4 + nbVarFrame * 4)) + "]");
            //            mem.add(e.getIdent());
        }
        if (e.e != null) {
            e.e.accept(this);
        }
        if (e.asmt != null) {

            e.asmt.accept(this);
        }
    }

    @Override
    public void visit(Call e) {
        e.label.accept(this);
        e.fargs.accept(this);

    }

    @Override
    public void visit(CallClo e) {
        e.ident.accept(this);
        e.fargs.accept(this);
    }

    @Override
    public void visit(Fadd e) {
        e.ident1.accept(this);
        e.ident2.accept(this);
    }

    @Override
    public void visit(Fargs e) {
        if (!e.estNIL) {
            e.ident.accept(this);
        }
        if (e.fargs != null) {//TODO: On a changé la structure des arguments
            e.fargs.accept(this);
        }
    }

    @Override
    public void visit(Fdiv e) {
        e.ident1.accept(this);
        e.ident2.accept(this);
    }

    @Override
    public void visit(Fmul e) {
        e.ident1.accept(this);
        e.ident2.accept(this);
    }

    @Override
    public void visit(Fneg e) {
        e.ident.accept(this);
    }

    @Override
    public void visit(Fsub e
    ) {
        e.ident1.accept(this);
        e.ident2.accept(this);
    }

    @Override
    public void visit(Fundefs e) {
        if (e.formal_args != null) {
            e.label.accept(this);
            e.formal_args.accept(this);
            e.asmt.accept(this);
//            e.fundefs.accept(this);
        } else if (e.asmt != null) {
            e.asmt.accept(this);
        } else {
            e.label.accept(this);
            e.ident.accept(this);
//            e.fundefs.accept(this);
        }
    }

    @Override
    public void visit(Ident e) {
        e.actionAllocateur = "save";

    }

    @Override
    public void visit(okalm.ocamlcompiler.java.asml.If e) {

        e.ifIdent.accept(this);
        e.ioi.accept(this);
        e.thenasmt.accept(this);
        if (e.elseasmt != null) {
            e.elseasmt.accept(this);

        }

    }

    @Override
    public void visit(okalm.ocamlcompiler.java.asml.Int e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visit(Label e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visit(Mem e) {
        e.ident1.accept(this);
        e.ioi.accept(this);
        if (e.ident2 != null) {
            e.ident2.accept(this);
        }
    }

    @Override
    public void visit(Neg e) {
        e.ident.accept(this);
    }

    @Override
    public void visit(New e) {
        e.ioi.accept(this);
    }

    @Override
    public void visit(Nop e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visit(ParenExp e) {
        e.e.accept(this);
    }

    @Override
    public void visit(Tokens e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
