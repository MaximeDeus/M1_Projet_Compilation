package okalm.backend;

import java.util.HashSet;
import java.util.Set;
import okalm.asml.*;
import okalm.tools.AsmlObjVisitor;

/**
 * Génére du code asml à partir d'un arbre asml
 *
 * @author defoursr
 */
public class printAsmlVisitor implements AsmlObjVisitor<String> {

    private Integer nbIndent;
    private Boolean indent;
    private Set<String> extFun;

    public printAsmlVisitor() {
        this.indent = false;
        nbIndent = 0;
        extFun = new HashSet();
        extFun.add("_print_int");
        extFun.add("_print_newline");
        extFun.add("_print_string");
        extFun.add("_exit");
        extFun.add("_hello_world");
        extFun.add("_print_char");
    }

    /**
     * @param indent option d'indentation
     */
    public printAsmlVisitor(Boolean indent) {
        this.indent = indent;
        nbIndent = 0;
        extFun = new HashSet();
        extFun.add("_print_int");
        extFun.add("_print_newline");
        extFun.add("_print_string");
        extFun.add("_exit");
        extFun.add("_hello_world");
        extFun.add("_print_char");
    }


    /**
     * @return indentation à ajouter en début de ligne
     */
    private String indentRepeator() {
        if (!indent) return "";
        String s = "";
        for (int i = 0; i < nbIndent; i++) {
            s = s + "   ";
        }
        return "\n" + s;
    }

    @Override
    public String visit(Add e) {
        return ("add " + e.ident.accept(this) + " " + e.identOrImm.accept(this));
    }

    @Override
    public String visit(Sub e) {
        return ("sub " + e.ident.accept(this) + " " + e.identOrImm.accept(this));
    }

    @Override
    public String visit(Asmt e) {
        return "let " + e.ident.accept(this) + " = " + e.e.accept(this) + " in " + this.indentRepeator() + e.asmt.accept(this);
    }

    @Override
    public String visit(Call e) {
        String temp = "";
        if(extFun.contains(e.label.accept(this))){
            temp+="_min_caml";
        }
        temp+=e.label.accept(this);
        String s = "call " + temp + " ";
        if (!e.fargs.isEmpty()) {
            for (Exp_asml elem : e.fargs) {
                s += elem.accept(this) + " ";
            }
        }
        return s;
    }

    @Override
    public String visit(CallClo e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String visit(Fadd e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String visit(Fargs e) {
        return e.ident.accept(this);
    }

    @Override
    public String visit(Fdiv e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String visit(Fmul e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String visit(Fneg e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String visit(Fsub e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String visit(Fundefs e) {
        String s = "";
        //fonctions à déclarer avant
        for (Exp_asml elem : e.fundefs) {

            s += elem.accept(this) + "";
        }

        //en tête de la fonction
        s += "\nlet " + e.label.accept(this);
        for (Exp_asml elem : e.formal_args) {
            s += " " + elem.accept(this);
        }
        s += " = ";
        nbIndent++;
        //corps de la fonction
        s += this.indentRepeator() + e.asmt.accept(this);
        nbIndent--;
        return s;
    }

    @Override
    public String visit(Ident e) {
        return e.ident;
    }

    @Override
    public String visit(If e) {
        String retour = indent ? "\n" : " ";
        nbIndent++;
        String s = "if " + e.condasmt.accept(this) + " then (" + indentRepeator() + e.thenasmt.accept(this);
        nbIndent--;
        s += indentRepeator() + ") else (";
        nbIndent++;
        s += indentRepeator() + e.elseasmt.accept(this);
        nbIndent--;
        s += indentRepeator() + ")" + indentRepeator();

        return s;

    }

    @Override
    public String visit(Int e) {
        return Integer.toString(e.e);
    }

    @Override
    public String visit(Label e) {
        return e.ident;
    }

    @Override
    public String visit(Mem e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String visit(Neg e) {
        return "neg " + e.ident.accept(this);
    }

    @Override
    public String visit(New e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String visit(Nop e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String visit(ParenExp e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String visit(Tokens e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String visit(Eq e) {
        return e.e1.accept(this) + " == " + e.e2.accept(this);
    }

    @Override
    public String visit(LE e) {
        return e.e1.accept(this) + " <= " + e.e2.accept(this);
    }
}
