/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package okalm.frontend;

import okalm.asml.*;
import okalm.ast.*;
import okalm.tools.ObjVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * Convertie une structure ast en structure asml (front end to back end)
 *
 * @author defoursr
 */
public class FrontToEndVisitor implements ObjVisitor<Exp_asml> {

    /**
     * encapsule le code dans la fonction main (let _) et ajoute et visite les
     * fonctions déclarées dans listeFun
     *
     * @param e        Exp_asml retourné après la visite du visiteur
     * @param listeFun liste de fonction retourné par le ClosureVisitor
     *                 (ClosureVisitor.listeFun)
     * @return l'arbre ecapsulé au format pour le BackEnd
     */
    public Exp_asml wrapCode(Exp_asml e, ArrayList<ClosureFunction> listeFun) {
        //création de la liste de fonction du main
        List<Exp_asml> listefunAsml = new ArrayList();

        //pour chaque fonction présente dans listeFun
        listeFun.forEach((ClosureFunction element) -> {

            //création le liste des arguments en format asml
            List<Exp_asml> params = new ArrayList();
            element.getParameters().forEach((stringElement) -> {
                params.add(new Fargs(new Ident(stringElement)));
            });

            //création d'une fonction sous forme asml
            listefunAsml.add(
                    new Fundefs( //fonction asml
                            element.getCode().accept(this), //code sous forme asml de la fonction
                            new ArrayList(), //liste de sous fonctions de la fonction //TODO
                            params, //liste de paramètres créée précédemment
                            new Label(element.getLabel()), //nom de la fonction
                            new Ident("")));
        });
        return new Fundefs(
                e,
                listefunAsml,
                new ArrayList(),
                new Label("_"),
                new Ident("")
        );

    }

    @Override
    public Exp_asml visit(Unit e) {
        return new Ident("");
    }

    @Override
    public Exp_asml visit(Bool e) {
        int i = e.b ? 1 : 0;
        return new okalm.asml.Int(i);
    }

    @Override
    public Exp_asml visit(okalm.ast.Int e) {
        return new okalm.asml.Int(e.i);
    }

    @Override
    public Exp_asml visit(okalm.ast.Float e) {
        return new Ident(java.lang.Float.toString(e.f));
    }

    @Override
    public Exp_asml visit(Not e) {
        return new okalm.asml.Neg(e.e.accept(this));
    }

    @Override
    public Exp_asml visit(okalm.ast.Neg e) {
        return new okalm.asml.Neg(e.e.accept(this));
    }

    @Override
    public Exp_asml visit(okalm.ast.Add e) {
        return new okalm.asml.Add(e.e1.accept(this), e.e2.accept(this));
    }

    @Override
    public Exp_asml visit(okalm.ast.Sub e) {
        return new okalm.asml.Sub(e.e1.accept(this), e.e2.accept(this));
    }

    @Override
    public Exp_asml visit(FNeg e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Exp_asml visit(FAdd e) {
        return new okalm.asml.Fadd(e.e1.accept(this), e.e2.accept(this));
    }

    @Override
    public Exp_asml visit(FSub e) {
        return new okalm.asml.Fsub(e.e1.accept(this), e.e2.accept(this));
    }

    @Override
    public Exp_asml visit(FMul e) {
        return new okalm.asml.Fmul(e.e1.accept(this), e.e2.accept(this));
    }

    @Override
    public Exp_asml visit(FDiv e) {
        return new okalm.asml.Fdiv(e.e1.accept(this), e.e2.accept(this));
    }

    @Override
    public Exp_asml visit(okalm.ast.Eq e) {
        return new okalm.asml.Eq(e.e1.accept(this), e.e2.accept(this));
    }

    @Override
    public Exp_asml visit(okalm.ast.LE e) {
        return new okalm.asml.LE(e.e1.accept(this), e.e2.accept(this));
    }

    @Override
    public Exp_asml visit(okalm.ast.If e) {
        return new okalm.asml.If(e.e1.accept(this), e.e2.accept(this), e.e3.accept(this));
    }

    @Override
    public Exp_asml visit(Let e) {
        return new Asmt(new Ident(e.id.id), e.e1.accept(this), e.e2.accept(this), false);
    }

    @Override
    public Exp_asml visit(Var e) {
        return new Ident(e.id.id);
    }

    @Override
    public Exp_asml visit(LetRec e) { //après la closure, on ne rencontre plus de let rec
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Exp_asml visit(App e) {
        List<Exp_asml> l = new ArrayList();

        //on visite tous les arguments de l'appel pour les transformer en noeuds asml et on les stocke dans une nouvelle liste asml
        e.es.forEach((element) -> {
            l.add(new Fargs(element.accept(this)));
        });
        Label label = new Label("_" + e.e.accept(this).toString());
        return new okalm.asml.Call(label, l);
    }

    @Override
    public Exp_asml visit(Tuple e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Exp_asml visit(LetTuple e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Exp_asml visit(Array e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Exp_asml visit(Get e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Exp_asml visit(Put e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
