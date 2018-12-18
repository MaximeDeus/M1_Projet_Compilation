/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package okalm.ocamlcompiler.java.asml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author liakopog
 */
public class CodeGenerationVisitor implements AsmlErrorVisitor<Exp_asml> {

    File fichierARM;
    FileWriter writer;
    ArrayList<String> memoire = new ArrayList<>();
    String[] registres = new String[13]; //Liste representant les registres r0-r12 utilisables par le programme
    String[] registresSpeciales = new String[3];
    int frameCounter = 0;

    public CodeGenerationVisitor() throws IOException {
        fichierARM = new File("./ARMcode.s");
        writer = new FileWriter(fichierARM);
    }

    @Override
    public String visit(Add e) throws Exception {

        return (e.ident.accept(this) + ", " + e.ioi.accept(this));
    }

    @Override
    public String visit(Sub e) throws Exception {
        return (e.ident.accept(this) + ", " + e.ioi.accept(this));

    }

    @Override
    public <E> E visit(Asmt e) throws Exception {
        if (e.ident != null) {
            switch (e.e.getClass().toString()) {

                case "Add":
                    String[] regs = findRegisters(e.ident.toString() + e.e.accept(this));

                    writer.write("ADD" + regs[0] + regs[1] + regs[2]);

            }

        }

    }

    @Override
    public <E> E visit(Call e) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <E> E visit(CallClo e) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <E> E visit(Fadd e) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <E> E visit(Fargs e) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <E> E visit(Fdiv e) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <E> E visit(Fmul e) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <E> E visit(Fneg e) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <E> E visit(Fsub e) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <E> E visit(Fundefs e) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <E> E visit(Ident e) throws Exception {
        if (e.actionAllocateur != null) {
            if (e.actionAllocateur.equals("save")) {
                if (e.registre == -1) {
                    int reg = findRegister(e);
                    writer.write("MOV " + "r" + reg + ", " + e.ident);
                }

                writer.write("STR " + "r" + reg + "fp+" + (frameCounter * 4 + 4));
                frameCounter++;
                memoire.add(e);
            } else if (e.actionAllocateur.equals("load")) {

                writer.write("LDR" +);

            }

            writer.write("");

        }
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

    @Override
    public <E> E visit(If e) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <E> E visit(Int e) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <E> E visit(Label e) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <E> E visit(Mem e) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <E> E visit(Neg e) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <E> E visit(New e) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <E> E visit(Nop e) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <E> E visit(ParenExp e) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <E> E visit(Tokens e) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private int findRegisters(String e) {
        return -1;
    }
}
