package okalm;

import okalm.ast.*;
import okalm.ast.Float;

import java.util.ArrayList;
import java.util.List;

public class ReductionLetExpressionVisitor implements ObjVisitor<Exp> {

    /**
     * Cet attribut stocke les parents lorsqu'un fils gauche est un Let
     * Nécessaire à la construction récursive de l'arbre (un parent devient fils droit dernier noeud)
     */
    private List<Let> parents = new ArrayList<>();
    //Permet de stocker la création récursive du fils droit
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

        if (e.e1 instanceof Let){ //Si fils gauche est un let
            parents.add(e); //On l'ajoute à la liste des parents
            return e.e1.accept(this); //On termine son exécution et appelle son fils gauche
        }

        if(e.e2 instanceof Let|| e.e2 instanceof LetRec){ //Si fils droit est un LetXXX
            filsDroit = e.e2.accept(this); //On stocke le résultat de l'appel (construction récursive) dans filsDroit
            return new Let (e.id, e.t, e.e1,filsDroit); //On renvoie le nouvel arbre construit (Nouvel arbre car nouvelle référence)
        }

        /**
         * Construction de l'arbre (fils droit)
         */
        if (parents.size() > 0){
                Let parent = parents.get(parents.size() - 1); //On prend le dernier parent ajouté
                parents.remove(parent);
                Let nouveauFilsDroit = new Let(parent.id, parent.t, e.e2, parent.e2); //Création du nouveau fils droit à partir du parent (cf algo)
                return new Let(e.id, e.t, e.e1, nouveauFilsDroit.accept(this)); //Construction récursive de l'arbre
        }
        return e;
    }

    @Override
    public Exp visit(Var e) {
        return e;
    }

    @Override
    public Exp visit(LetRec e) {
        if (e.e instanceof Let || e.e instanceof LetRec){ //Si le in est un LetXXX
            return new LetRec(e.fd,e.e.accept(this)); //On applique l'algorithme sur la partie droite (nouvelle référence)
        }
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
