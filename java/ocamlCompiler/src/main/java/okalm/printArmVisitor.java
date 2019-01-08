/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package okalm;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import okalm.asml.*;
/**
 *
 * @author defoursr
 */
public class printArmVisitor implements AsmlObjVisitor<String>{
    
    private int labelNum;
    private Set<String> extFun;
    
    public printArmVisitor(){
        labelNum=0;
        extFun = new HashSet();
        extFun.add("print_int");
        extFun.add("print_newline");
        extFun.add("print_string");
        extFun.add("exit");
        extFun.add("hello_world");
        extFun.add("print_char");
    }
    
    public String getNewLabel(){
        labelNum++;
        return "label"+labelNum;
    }

    @Override
    public String visit(Add e) {
        return e.ident.accept(this)+" , "+e.ioi.accept(this);
    }

    @Override
    public String visit(Sub e) {
        return e.ident.accept(this)+" , "+e.ioi.accept(this);
    }

    @Override
    public String visit(Asmt e) {
        String type = e.e.getClass().getSimpleName();
        String s = "";
        switch(type){
            case "Add":
                s+= "   ADD "+ e.ident.accept(this)+" , "+ e.e.accept(this) + "\n";
                break;
                
            case "Sub":
                break;
            
            case "Int":
                s+= "   MOV "+ e.ident.accept(this)+" , "+ e.e.accept(this) +"\n";
                break;
                
            case "Ident":
                s+= "   MOV "+ e.ident.accept(this)+" , "+ e.e.accept(this)+"\n";
                break;
                
            default: 
                s+= "!"+type +" is not supported yet!";
                break;
        }
        //TODO
        s+=e.asmt.accept(this)+"\n";
        return s;        
    }

    @Override
    public String visit(Call e) {
        if (extFun.contains(e.label.accept(this))){
            String s = "";
            for(Exp_asml elem: e.fargs){
                s+= ","+elem.accept(this);
            }
            return e.label.accept(this) +"("+s+")";
        }else{
            return "!Function call not supported yet!\n";
        }
    }

    @Override
    public String visit(CallClo e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String visit(Fundefs e) {
        String s="";
        if(e.label.accept(this).equals("_")){
            s+=e.label+"main:\n";
        }else{
            s+=e.label+s+":\n";
        }
        s+=e.asmt.accept(this)+"\n";
        return s;
    }

    @Override
    public String visit(Ident e) {
        return e.ident;
    }

    @Override
    public String visit(If e) {
        String s = "";
        s+= "   CMP "+ e.condasmt.accept(this)+"\n";                           //comparaison des deux éléments
        s+=e.condasmt.getClass().getSimpleName().equals("Eq")?"EQ ":"LE ";  //séléction du comparateur (EQ/LE)
        String t = getNewLabel(); // création du label du cas true
        s+= t + "\n";
        s+= e.elseasmt.accept(this)+"\n";
        String end = getNewLabel(); //création du label de fin;
        s+= end + "\n"; // renvois de la phase else au label de fin
        s+= "."+t+":\n"; //label true
        s+= e.thenasmt.accept(this)+"\n";
        s+= "."+end+":\n";
        
        return s;
    }

    @Override
    public String visit(Int e) {
        return "#"+e.e;
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
    public String visit(Eq e) {
        return e.e1.accept(this)+" , "+e.e2.accept(this);
    }

    @Override
    public String visit(LE e) {
        return e.e1.accept(this)+" , "+e.e2.accept(this);
    }
    
}
