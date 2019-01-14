/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package okalm.frontend;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import okalm.tools.ObjVisitor;
import okalm.ast.*;
import okalm.ast.Float;

/**
 *
 * @author defoursr
 */
public class ClosureVisitor implements ObjVisitor<Exp> {
    
    public ArrayList<ClosureFunction> listeFun;
    
    public ClosureVisitor(){
        listeFun= new ArrayList();
    }
    
    public String functionsToString(){
        String s ="";
        for(ClosureFunction c: listeFun){
            s+=c.toString();
        }
        return s;
    }
    
    @Override
    public Exp visit(Unit e) {
        return e;
    }

    @Override
    public Exp visit(Bool e) {
        return e;
    }

    @Override
    public Exp visit(Int e) {
        return e;
    }

    @Override
    public Exp visit(Float e) {
        return e;
    }

    @Override
    public Exp visit(Not e) {
        return new Not(e.e.accept(this));
    }

    @Override
    public Exp visit(Neg e) {
        return new Neg(e.e.accept(this));
    }

    @Override
    public Exp visit(Add e) {
        return new Add(e.e1.accept(this), e.e2.accept(this));
    }

    @Override
    public Exp visit(Sub e) {
        return new Sub(e.e1.accept(this), e.e2.accept(this));
    }

    @Override
    public Exp visit(FNeg e) {
        return new FNeg(e.e.accept(this));
    }

    @Override
    public Exp visit(FAdd e) {
        return new FAdd(e.e1.accept(this), e.e2.accept(this));
    }

    @Override
    public Exp visit(FSub e) {
        return new FSub(e.e1.accept(this), e.e2.accept(this));
    }

    @Override
    public Exp visit(FMul e) {
        return new FMul(e.e1.accept(this), e.e2.accept(this));
    }

    @Override
    public Exp visit(FDiv e) {
        return new FDiv(e.e1.accept(this), e.e2.accept(this));
    }

    @Override
    public Exp visit(Eq e) {
        return new Eq(e.e1.accept(this), e.e2.accept(this));
    }

    @Override
    public Exp visit(LE e) {
        return new LE(e.e1.accept(this), e.e2.accept(this));
    }

    @Override
    public Exp visit(If e) {
        return new If(e.e1.accept(this), e.e2.accept(this), e.e3.accept(this));
    }

    @Override
    public Exp visit(Let e) {
        return new Let(e.id, e.t, e.e1.accept(this), e.e2.accept(this));
    }

    @Override
    public Exp visit(Var e) {
        return new Var(e.id);
    }

    @Override
    public Exp visit(LetRec e) {
        FunDef f = e.fd;
        
        //liste de paramètres convertie en liste de string
        Set<String> s = new HashSet<String>();
        f.args.forEach((element) -> {
            s.add(element.id);
        });
        
        Exp cpyCode = f.e.accept(this);
        
        ClosureFunction c = new ClosureFunction(
                "_"+f.id.id, //label
                s, //arguments
                cpyCode //code
        );
        //ajout de la fonction
        listeFun.add(c);
        
        return(e.e.accept(this));
    }
    @Override
    public Exp visit(App e) {
        List<Exp> temp = new ArrayList();
        e.es.forEach((element) -> {
            temp.add(element.accept(this));
        });
        return new App(e.e.accept(this),temp);
    }

    @Override
    public Exp visit(Tuple e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Exp visit(LetTuple e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Exp visit(Array e) {
        return new Array(e.e1.accept(this), e.e2.accept(this));
    }

    @Override
    public Exp visit(Get e) {
        return new Get(e.e1.accept(this), e.e2.accept(this));
    }

    @Override
    public Exp visit(Put e) {
        return new Put(e.e1.accept(this), e.e2.accept(this), e.e3.accept(this));
    }
    
}
