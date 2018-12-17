/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package okalm.ocamlcompiler.java;

import java.util.ArrayList;
import java.util.List;
import okalm.ocamlcompiler.java.ast.*;

/**
 *
 * @author defoursr
 */
public class AlphaConversionVisitor implements ObjVisitor<Exp> {

    private ArrayList<String> listeId;

    private Integer numfun;
    private Integer funCount;

    public AlphaConversionVisitor() {
        listeId = new ArrayList<>();
        numfun = 0;
        funCount = 0;
    }

    /**
     * renomme la variable suivant le Int associé dans la map
     *
     * @param id
     * @return
     */
    private Id rename(Id id) {
        //Integer i = listeId.get(id);
        if (numfun == 0) {
            return new Id(id.id); //x
        } else {
            return new Id(id.id + numfun.toString()); //x1 ou x2 ou...
        }
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
    public Exp visit(okalm.ocamlcompiler.java.ast.Float e) {
        return e;
    }

    @Override
    public Exp visit(Not e) {
        return new Not(e.accept(this));
    }

    @Override
    public Exp visit(Neg e) {
        return new Neg(e.accept(this));
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
        return new Let(rename(e.id), e.t, e.e1.accept(this), e.e2.accept(this));
    }

    @Override
    public Exp visit(Var e) {
        //variable avec id renommée
        return new Var(rename(e.id));
    }

    @Override
    public Exp visit(LetRec e) {
        funCount++;
        numfun+=funCount;
        FunDef f = e.fd;
        List<Id> temp = new ArrayList();
        f.args.forEach((element)-> {
            temp.add(rename(element));
        });
        FunDef fd = new FunDef(f.id,f.type,temp,f.e.accept(this));
        numfun-=funCount;
        LetRec lr = new LetRec(fd, e.e.accept(this));
        return(lr);
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
