package okalm;

import okalm.ast.*;
import okalm.ast.Float;

public class ReductionLetExpressionVisitor implements ObjVisitor<Exp> {

    /*Cet attribut est utilisé pour conserver le noeud précédent lors
     * de la construction du nouvel arbre (début nouvel arbre = fin du précédent)
     */
    private Let expressionPrecedente = null;

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
        Let filsGauche;
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

        /**Let filsDroit = (Let) e.e2;
          *while (filsDroit instanceof Let){ //Si "in Let ..."
          *    filsDroit = (Let) e.e2;
          *    expressionPrecedente = new Let(filsDroit.id, filsDroit.t, filsDroit.e1, expressionPrecedente);
          *    }*/
            else {
            if (expressionPrecedente == null) { //Pas de réduction à effectuer
                return e;
            } else { //Fin de l'algorithme
                return new Let(e.id, e.t, e.e1, expressionPrecedente);
            }

        }
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
