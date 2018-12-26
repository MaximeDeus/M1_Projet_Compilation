package okalm;

import okalm.ast.*;
import okalm.ast.Float;

import java.util.ArrayList;
import java.util.List;

public class ReductionLetExpressionVisitor implements ObjVisitor<Exp> {

    /**
     * Cet attribut stocke les parents lorsqu'un fils gauche est un Let
     * Nécessaire à la construction récursive de l'arbre
     */
    private List<Let> parents = new ArrayList<>();
    //L'arbre construit
    private Exp tree;

    private Exp filsGauche;
    private Exp filsDroit;

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
        return e;
    }

    @Override
    public Exp visit(Neg e) {
        return e;
    }

    @Override
    public Exp visit(Add e) {
        return e;
    }

    @Override
    public Exp visit(Sub e) {
        return e;
    }

    @Override
    public Exp visit(FNeg e) {
        return e;
    }

    @Override
    public Exp visit(FAdd e) {
        return e;
    }

    @Override
    public Exp visit(FSub e) {
        return e;
    }

    @Override
    public Exp visit(FMul e) {
        return e;
    }

    @Override
    public Exp visit(FDiv e) {
        return e;
    }

    @Override
    public Exp visit(Eq e) {
        return e;
    }

    @Override
    public Exp visit(LE e) {
        return e;
    }

    @Override
    public Exp visit(If e) {
        return e;
    }

    @Override
    public Exp visit(Let e) {
        /**
         * Version 1 (non fonctionnelle)
         * Let filsGauche;
        if (e.e1 instanceof Let) { //Si "= Let..."
            filsGauche = (Let) e.e1;
            if (expressionPrecedente == null) { //Si premier Let
                //Début du nouvel arbre
                expressionPrecedente = new Let(e.id, e.t, filsGauche.e2, e.e2);
            } else {
                //Construction de l'arbre de bas en haut (bas = racine de l'arbre initial)
                expressionPrecedente = new Let(e.id, e.t, filsGauche.e2, expressionPrecedente);
            }
            return filsGauche.accept(this); //On parcourt le Let
        }

        Let filsDroit = (Let) e.e2;
          while (filsDroit instanceof Let){ //Si "in Let ..."
              filsDroit = (Let) e.e2;
              expressionPrecedente = new Let(filsDroit.id, filsDroit.t, filsDroit.e1, expressionPrecedente);
              }
            else {
            if (expressionPrecedente == null) { //Pas de réduction à effectuer
                return e;
            } else { //Fin de l'algorithme
                return new Let(e.id, e.t, e.e1, expressionPrecedente);
            }

        }
         */
        //TODO Avant tester code, penser à bien config Commande.java (prendre exp initiale, pas Knorm etc.)
        /**
         * Version 2 (non fonctionnelle)
        Exp filsGauche = e.e1;
        if (filsGauche instanceof Let){ //TODO Ajouter traitement pour LetTuple et LetRec
            filsGauche.accept(this);
            //On récupère le fils droit
            Exp filsDroit = ((Let) filsGauche).e2;
            while (filsDroit instanceof Let){
                filsDroit = ((Let) filsDroit).e2;
            }
            root = new Let(((Let) filsGauche).id,((Let) filsGauche).t,filsGauche,
                    new Let(e.id,e.t,filsDroit,e.e2));
        }

        e.e2.accept(this);
        return root;
         */

        /**
         * Si le fils gauche est un bloc imbriqué :
         *
         * 1- On sauvegarde le noeud parent qui sera à terme ajouté en tant que fils droit
         * 2- On appelle le fils gauche
         */
        if (e.e1 instanceof Let){
            //TODO pose problème de référencement : Let filsGauche = (Let) e.e1;
            parents.add(e);
            return e.e1.accept(this);
            //TODO Essayer return e.e1.accept(this);
            //TODO Essayer return new let (e.id, e.t, e.e1 (stocker dans var filsGauche?), e.e2);
        }

        /**
         * Si le fils droit est un bloc imbriqué :
         * 1- On renvoie le noeud parent + appel sur le fils droit
         */
        if(e.e2 instanceof Let){
            filsDroit = e.e2.accept(this);
           // return e;
            return new Let (e.id, e.t, e.e1,filsDroit);
        }

        /**
         * Construction de l'arbre
         */
        if (parents.size() > 0){
            Let parent = parents.get(parents.size()-1);
            parents.remove(parent);
            Let nouveauFilsDroit = new Let( parent.id,parent.t,e.e2,parent.e2);
            return new Let (e.id,e.t,e.e1,nouveauFilsDroit.accept(this));
            //return new Let (e.id,e.t,e.e1,new Let(
            //        parent.id,parent.t,e.e2,parent.e2));
        }
        return e;
        //return tree;
    }

    @Override
    public Exp visit(Var e) {
        return e;
    }

    @Override
    public Exp visit(LetRec e) {
        return e;
    }

    @Override
    public Exp visit(App e) {
        return e;
    }

    @Override
    public Exp visit(Tuple e) {
        return e;
    }

    @Override
    public Exp visit(LetTuple e) {
        return e;
    }

    @Override
    public Exp visit(Array e) {
        return e;
    }

    @Override
    public Exp visit(Get e) {
        return e;
    }

    @Override
    public Exp visit(Put e) {
        return e;
    }
}
