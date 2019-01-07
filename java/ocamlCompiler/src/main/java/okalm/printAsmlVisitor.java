/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package okalm;

import okalm.asml.*;

/**
 *
 * @author defoursr
 */
public class printAsmlVisitor implements AsmlObjVisitor<String>{
    
    private Integer nbIndent;
    private Boolean indent;
    
    public printAsmlVisitor(){
        this.indent=false;
    }
    
    /**
     * 
     * @param indent option d'indentation
     */
    public printAsmlVisitor(Boolean indent){
        this.indent=indent;
        nbIndent = 0;
    }
    
    private String indentRepeator(){
        if(!indent) return "";
        String s= "";
        for(int i = 0; i<nbIndent;i++){
            s=s+"   ";
        }
        return "\n"+s;
    }

    @Override
    public String visit(Add e) {
        return ("add "+e.ident.accept(this) +" "+e.ioi.accept(this));
    }

    @Override
    public String visit(Sub e) {
        return ("sub "+e.ident.accept(this) +" "+e.ioi.accept(this));
    }

    @Override
    public String visit(Asmt e) {
        return "Let "+e.ident.accept(this)+" = "+e.e.accept(this)+" in "+this.indentRepeator()+e.asmt.accept(this);
    }

    @Override
    public String visit(Call e) {
        String s= "call "+e.label.accept(this)+" ";
        if(!e.fargs.isEmpty()){
            for(Exp_asml elem : e.fargs){
                s+=elem.accept(this)+" ";
            }
        }
        return s;
    }

    @Override
    public String visit(CallClo e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String visit(Fadd e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String visit(Fargs e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String visit(Fdiv e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String visit(Fmul e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String visit(Fneg e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String visit(Fsub e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String visit(Fundefs e) {
//let _f x y =
//   let z = add x y in
//   let t = 2 in
//   sub t z
//
//let _ =
//   let x = 0 in
//   let y = 1 in
//   let z = call _f x y in
//   call _min_caml_print_int z
        String s="";
        //fonctions à déclarer avant
        for(Exp_asml elem : e.fundefs){
            s+=elem.accept(this)+"\n";
        }
        
        //en tête de la fonction
        s ="Let "+e.label.accept(this);
        for(Exp_asml elem : e.formal_args){
            s+=elem.accept(this)+" ";
        }
        nbIndent++;
        //corps de la fonction
        s+= this.indentRepeator()+ e.asmt.accept(this);
        nbIndent--;
        return s;
    }

    @Override
    public String visit(Ident e) {
        return e.ident;
    }

    @Override
    public String visit(If e) {
//        But:
//        if a = b then (
//            let res1 = 0 in
//            call _min_caml_print_int res1
//        ) else (
//            let res2 = 1 in
//            call _min_caml_print_int res2 
//        )
        String retour = indent?"\n":" ";
        nbIndent++;
        String s = "if " + e.condasmt.accept(this) + " then ("+ indentRepeator() + e.thenasmt.accept(this) +retour;
        nbIndent--;
        s+= indentRepeator() + ") else ("+retour;
        nbIndent++;
        s+=indentRepeator()+e.elseasmt.accept(this);
        nbIndent--;
        s+=indentRepeator()+retour;
        
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String visit(Neg e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String visit(New e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String visit(Nop e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String visit(ParenExp e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String visit(Tokens e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}