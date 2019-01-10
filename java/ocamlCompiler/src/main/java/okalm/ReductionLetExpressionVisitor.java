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
        return new Add(e.e1.accept(this), e.e2.accept(this));
    }

    @Override
    public Exp visit(Sub e) {
        return new Sub(e.e1.accept(this), e.e2.accept(this));
    }

    @Override
    public Exp visit(FNeg e) {
        return e;
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
        if (e.e1 instanceof Let) { //Si fils gauche est un let
            parents.add(e); //On l'ajoute à la liste des parents
            return e.e1.accept(this); //On termine son exécution et appelle son fils gauche
        }

        if (e.e2 instanceof Let
                || e.e2 instanceof LetRec
                || e.e2 instanceof If
                || e.e2 instanceof Add
                || e.e2 instanceof LE
                || e.e2 instanceof Eq
                || e.e2 instanceof FDiv
                || e.e2 instanceof FMul
                || e.e2 instanceof FSub
                || e.e2 instanceof FAdd
                || e.e2 instanceof Sub
                ) { //Si fils droit est un noeud parent
            filsDroit = e.e2.accept(this); //On stocke le résultat de l'appel (construction récursive) dans filsDroit
            //return new Let(e.id, e.t, e.e1, filsDroit); //On renvoie le nouvel arbre construit (Nouvel arbre car nouvelle référence)
            e = new Let(e.id, e.t, e.e1, filsDroit);
        }

        /**
         * Construction de l'arbre (fils droit)
         */
        if (parents.size() > 0) {
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

        if (e.fd.e instanceof Let) { //Si le fils gauche est un Let (Pas un LetRec)
            FunDef new_fd = new FunDef(e.fd.id, e.fd.type, e.fd.args, e.fd.e.accept(this));
            return new LetRec(new_fd, e.e.accept(this)); //On a appliqué l'algorithme sur le fils gauche du LetRec
        } else {
            return new LetRec(e.fd, e.e.accept(this)); //On applique l'algorithme sur la partie droite (nouvelle référence)
        }
    }

    @Override
    public Exp visit(App e) {
        return e.e.accept(this);
    }

    @Override
    public Exp visit(Tuple e) {
        return e;
    }

    @Override
    public Exp visit(LetTuple e) {
        return new LetTuple(e.ids, e.ts, e.e1, e.e2.accept(this)); //On applique l'algorithme sur la partie droite (nouvelle référence)
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
