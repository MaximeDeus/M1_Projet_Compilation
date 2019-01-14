/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package okalm.frontend;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import okalm.tools.Id;
import okalm.tools.ObjVisitor;
import okalm.ast.*;
import okalm.ast.Float;

/**
 *
 * @author defoursr
 */
public class AlphaConversionVisitor implements ObjVisitor<Exp> {

    private HashSet<String> listeFun;

    private Integer numfun; // représente l'environemment actuel (0=main, 1=f1, 2=f2 etc)
    private Integer funCount; //compteur de numéro de fonction (1 fonction = 1 numéro, main = 0)
    private Boolean closure;

    public AlphaConversionVisitor() {
        listeFun = new HashSet();
        //Fonctions de la librairie mincaml
        listeFun.add("print_int");
        listeFun.add("print_newline");
        listeFun.add("print_string");
        listeFun.add("exit");
        listeFun.add("hello_world");
        listeFun.add("print_char");
        numfun = 0;
        funCount = 0;
        closure = false;
    }

    /**
     * Renomme une variable suivant le numéro de la fonction dans laquelle on se trouve (main=0)
     *
     * @param id
     * @return
     */
    private Id rename(Id id) {
        if (numfun == 0 || listeFun.contains(id.id)) {
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
        return new Let(rename(e.id), e.t, e.e1.accept(this), e.e2.accept(this));
    }

    @Override
    public Exp visit(Var e) {
        //variable avec id renommée suivant la fonction actuelle
        return new Var(rename(e.id));
    }

    @Override
    public Exp visit(LetRec e) {
        Boolean b = false;
        
        if (closure==false) {
            funCount++; //nouvelle fonction = nouveau numéro de fonction
            closure = true;
            b=true;
        }

        this.listeFun.add(e.fd.id.id); //ajout du nom de la fonction à la liste de fonction connue

        Integer previousFun = numfun; //on enregistre la fonction dans laquelle on se trouvait (numfun = fonction actuelle)
        numfun = funCount; //on se positionne dans la nouvelle fonction

        //copie des arguments de la fonction
        FunDef f = e.fd;
        List<Id> temp = new ArrayList();
        f.args.forEach((element) -> {
            temp.add(rename(element));
        });

        //création de l'en-tête de la fonction et alpha conversion du corps de la fonction (f.e)
        FunDef fd = new FunDef(f.id, f.type, temp, f.e.accept(this));
        numfun = previousFun;//retour dans la fonction précédente

        //alpha conversion de la partie après le 'in' de la fonction
        if(b){
            closure = false;
        }
        LetRec lr = new LetRec(fd, e.e.accept(this));
        
        return (lr);

    }

    @Override
    public Exp visit(App e) {
        List<Exp> temp = new ArrayList();
        e.es.forEach((element) -> {
            temp.add(element.accept(this));
        });
        return new App(e.e.accept(this), temp);
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
