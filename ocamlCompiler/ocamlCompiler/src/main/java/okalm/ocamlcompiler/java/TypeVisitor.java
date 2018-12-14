/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package okalm.ocamlcompiler.java;

import java.util.HashMap;
import java.util.Map;
import okalm.ocamlcompiler.java.ast.*;
import okalm.ocamlcompiler.java.type.*;




/**
 *
 * @author defoursr DiardJul
 */
public class TypeVisitor implements ObjErrorVisitor<Type>{
    
    private Map<String,Type> listeId;
    public TypeVisitor(){
        listeId = new HashMap();
    }
    /**
     * Ajoute un Id à l'environnement actuel
     * @param id new Id to add to the environement
     * @param t type to associate with the Id
     * @throws TypeException if the Id has already been declared before
     */
    private void addId(Id id, Type t) throws TypeException{
        if(listeId.containsKey(id)){
            throw new TypeException("Declaration error: variable "+ id.toString() +" declared twice or more");
        }
        else listeId.put(id.id, t);
    }
    
    @Override
    public Type visit(Unit e) {
        return new TUnit();
    }

    @Override
    public Type visit(Bool e) {
        return new TBool();
    }

    @Override
    public Type visit(Int e) {
        return new TInt();
    }

    @Override
    public Type visit(okalm.ocamlcompiler.java.ast.Float e) {
        return new TFloat();
    }

    @Override
    public Type visit(Not e) throws Exception{
        Type t = e.e.accept(this);
        if(!t.getClass().getSimpleName().equals("TBool")){ //si le type du fils est différent du type entre guillements
            throw new TypeException("Bad typing at Not: boolean expected");
        }
        return t;
    }

    @Override
    public Type visit(Neg e) throws Exception{
        Type t = e.e.accept(this);
        if(!t.getClass().getSimpleName().equals("TInt")){ //si le type du fils est différent du type entre guillements
            throw new TypeException("Bad typing at Neg: Integer expected");
        }
        return t;
    }

    @Override
    public Type visit(Add e) throws Exception {
        Type t1 = e.e1.accept(this);
        Type t2 = e.e2.accept(this);
        if(!t1.getClass().getSimpleName().equals("TInt") ||
           !t2.getClass().getSimpleName().equals("TInt") ){ //si un type d'un des fils est différent du type entre guillements
            throw new TypeException("Bad typing at Add: Integer expected");
        }
        return t1;
    }

    @Override
    public Type visit(Sub e) throws Exception {
        Type t1 = e.e1.accept(this);
        Type t2 = e.e2.accept(this);
        if(!t1.getClass().getSimpleName().equals("TInt") ||
           !t2.getClass().getSimpleName().equals("TInt") ){ //si un type d'un des fils est différent du type entre guillements
            throw new TypeException("Bad typing at Sub: Integer expected");
        }
        return t1;
    }

    @Override
    public Type visit(FNeg e) throws Exception {
        Type t = e.e.accept(this);
        if(!t.getClass().getSimpleName().equals("TFloat")){ //si le type du fils est différent du type entre guillements
            throw new TypeException("Bad typing at FNeg: Float expected");
        }
        return t;    }

    @Override
    public Type visit(FAdd e) throws Exception {
        Type t1 = e.e1.accept(this);
        Type t2 = e.e2.accept(this);
        if(!t1.getClass().getSimpleName().equals("TFloat") ||
           !t2.getClass().getSimpleName().equals("TFloat") ){ //si un type d'un des fils est différent du type entre guillements
            throw new TypeException("Bad typing at FAdd: Float expected");
        }
        return t1;
    }

    @Override
    public Type visit(FSub e) throws Exception {
        Type t1 = e.e1.accept(this);
        Type t2 = e.e2.accept(this);
        if(!t1.getClass().getSimpleName().equals("TFloat") ||
           !t2.getClass().getSimpleName().equals("TFloat") ){ //si un type d'un des fils est différent du type entre guillements
            throw new TypeException("Bad typing at FSub: Float expected");
        }
        return t1;
    }

    @Override
    public Type visit(FMul e) throws Exception {
        Type t1 = e.e1.accept(this);
        Type t2 = e.e2.accept(this);
        if(!t1.getClass().getSimpleName().equals("TFloat") ||
           !t2.getClass().getSimpleName().equals("TFloat") ){ //si un type d'un des fils est différent du type entre guillements
            throw new TypeException("Bad typing at FMul: Float expected");
        }
        return t1;
    }

    @Override
    public Type visit(FDiv e) throws Exception {
        Type t1 = e.e1.accept(this);
        Type t2 = e.e2.accept(this);
        if(!t1.getClass().getSimpleName().equals("TFloat") ||
           !t2.getClass().getSimpleName().equals("TFloat") ){ //si un type d'un des fils est différent du type entre guillements
            throw new TypeException("Bad typing at FDiv: Float expected");
        }
        return t1;
    }

    @Override
    public Type visit(Eq e) throws Exception {
        Type t1 = e.e1.accept(this);
        Type t2 = e.e2.accept(this);
        if(!t1.getClass().getSimpleName().equals(t2.getClass().getSimpleName().toString())){
             throw new TypeException("Bad typing at Eq: same type expected");
        }
        return new TBool();
    }

    @Override
    public Type visit(LE e) throws Exception {
        Type t1 = e.e1.accept(this);
        Type t2 = e.e2.accept(this);
        if(!t1.getClass().getSimpleName().equals(t2.getClass().getSimpleName().toString()) && 
          (!t1.getClass().getSimpleName().equals("TFloat") ||!t1.getClass().getSimpleName().equals("TInt"))){
             throw new TypeException("Bad typing at Eq: same type expected");
        }
        return new TBool();
    }

    @Override
    public Type visit(If e) throws Exception {
        Type t1 = e.e1.accept(this);
        Type t2 = e.e2.accept(this);
        Type t3 = e.e3.accept(this);
        
        return t1;
    }

    @Override
    public Type visit(Let e) throws Exception {
    // Let (x : id_type) = exrp1 in expr2
    // expr1 must be of the type id_type, so we generate 
    // GenEquations(env, expr1, id_type) 
    // expr2 must be of type "type", but in a new environment where
    // x is of type id_type, so we generate
    // GenEquations(env + (id -> id_type), expr2, type)
        Type t1 = e.e1.accept(this);
        this.addId(e.id, t1);
        Type t2 = e.e2.accept(this);
        return t2;
    }

    @Override
    public Type visit(Var e) throws Exception {

        if(!listeId.containsKey(e.id.id)){
            throw new TypeException("Unknow variable "+ e.id.id);
        }
        return listeId.get(e.id.id);
    }

    @Override
    public Type visit(LetRec e) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Type visit(App e) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Type visit(Tuple e) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Type visit(LetTuple e) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Type visit(Array e) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Type visit(Get e) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Type visit(Put e) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
