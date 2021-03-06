package okalm.typechecking;

import okalm.ast.*;
import okalm.tools.Id;
import okalm.tools.ObjErrorVisitor;
import okalm.type.*;

import java.util.*;

/**
 * @author defoursr DiardJul
 */
public class TypeVisitor implements ObjErrorVisitor<Type> {


    private Map<String, Type> listeVar;
    private Map<String, Type> listeFun;
    private Map<String, Type> listeArray;
    private Map<String, List<Type>> listeTuple;
    private Map<Exp, Type> listeExp;
    private Map<Exp, List<Type>> listeExpTuple;
    private int num_let;

    /**
     * @param n pour savoir a quel let on est (toujours égal à zéro)
     */
    public TypeVisitor(int n) {
        listeVar = new HashMap();
        listeFun = new HashMap();
        listeArray = new HashMap();
        listeTuple = new HashMap();
        listeExp = new HashMap();
        listeExpTuple = new HashMap();
        listeFun.put("print_int", new TUnit());
        listeFun.put("truncate", new TInt());
        listeFun.put("print_string", new TUnit());
        listeFun.put("cos", new TFloat());
        listeFun.put("sin", new TFloat());
        listeFun.put("tan", new TFloat());
        listeFun.put("sqrt", new TFloat());
        listeFun.put("abs_float", new TFloat());
        listeFun.put("int_of_float", new TFloat());
        listeFun.put("float_of_int", new TInt());
        num_let = n;

    }

    /**
     * Ajout d'un Id à l'environnement actuel
     *
     * @param l  nom de la map
     * @param id nouvel identifiant a ajouter à la map
     * @param t  type à associer avec l'identifiant
     * @throws TypeException si l'identifiant est déjà utilisé
     */
    private void addId(Map<String, Type> l, Id id, Type t) throws TypeException {
        if (l == listeFun && l.containsKey(id.id)) {
            listeFun.remove(id.id);
        }
        if (!l.isEmpty() && l.containsKey(id.id)) {
            throw new TypeException("Declaration error : variable " + id.toString() + " declared twice or more");
        } else {
            l.put(id.id, t);
        }
    }

    /**
     * Ajout d'une expression à l'environnement d'expression actuel
     *
     * @param exp expression concernée, identifiée par sa référence
     * @param t   type associé à l'expression
     */
    private void addExpType(Exp exp, Type t) {
        listeExp.put(exp, t);
    }

    /**
     * Ajout à la liste de type représentant les éléments d'un tuple et fait la liaison avec l'expression (non utilisé et non utilisable)
     *
     * @param exp expression concernée, identifiée par son adresse
     * @param t   liste de type associé à l'expression
     */
    private void addExpTuple(Exp exp, ArrayList<Type> t) {
        listeExpTuple.put(exp, (List) t);
    }

    /**
     * Compare un type avec une classe, renvoie true si elles sont égales
     *
     * @param t type
     * @param s classe (TInt, TBool, etc)
     * @return boolean
     */
    private Boolean testType(Type t, Class s) {
        return t.getClass().getSimpleName().equals(s.getSimpleName());
    }

    /**
     * Visite une feuille Unit et renvoie un Type Tunit
     *
     * @param e expression étudiée
     * @return TUnit
     */
    @Override
    public Type visit(Unit e) {
        return new TUnit();
    }

    /**
     * Visite une feuille Bool et renvoie un Type TBool
     *
     * @param e expression étudiée
     * @return TBool
     */
    @Override
    public Type visit(Bool e) {
        return new TBool();
    }

    /**
     * Visite une feuille Int et renvoie un Type TInt
     *
     * @param e expression étudiée
     * @return TInt
     */
    @Override
    public Type visit(Int e) {
        return new TInt();
    }

    /**
     * Visite une feuille Float et renvoie un Type TFloat
     *
     * @param e expression étudiée
     * @return TFloat
     */
    @Override
    public Type visit(okalm.ast.Float e) {
        return new TFloat();
    }

    /**
     * Visite un nœud Not et vérifie que son fils renvoie bien un type TBool. Renvoie un type TBool.
     *
     * @param e expression étudiée
     * @return TBool
     * @throws Exception si son fils n'est pas un TBool
     */
    @Override
    public Type visit(Not e) throws Exception {
        Type t = e.e.accept(this);
        if (this.testType(t, TUndef.class)) {
            return new TBool();
        }
        if (!testType(t, TBool.class)) {
            throw new TypeException("Bad typing at Not: boolean expected");
        }
        return new TBool();
    }

    /**
     * Visite un nœud Neg et vérifie que le fils renvoie bien un type TInt. Renvoie un type TInt.
     *
     * @param e expression étudiée
     * @return TInt
     * @throws Exception si son fils n'est pas un TInt
     */
    @Override
    public Type visit(Neg e) throws Exception {
        Type t = e.e.accept(this);
        if (testType(t, TUndef.class)) {
            VarVisitor vv = new VarVisitor();
            Set<String> h = e.e.accept(vv);
            for (String s : h) {
                listeVar.replace(s, new TInt());
            }
            return new TInt();
        }
        if (!testType(t, TInt.class)) {
            throw new TypeException("Bad typing at Neg: Integer expected");
        }
        return new TInt();
    }

    /**
     * Visite un nœud Add, vérifie si ses deux fils renvoient des types Tint. Renvoie un Type Tint.
     *
     * @param e expression étudiée
     * @return TInt
     * @throws Exception si son fils n'est pas un TInt
     */
    @Override
    public Type visit(Add e) throws Exception {
        Type t1 = e.e1.accept(this);
        Type t2 = e.e2.accept(this);
        if (testType(t1, TUndef.class)) {
            t1 = t2;
            VarVisitor vv = new VarVisitor();
            Set<String> h = e.e1.accept(vv);
            for (String s : h) {
                listeVar.replace(s, new TInt());
            }
        }

        if (testType(t2, TUndef.class)) {
            t2 = t1;
        }

        if ((!testType(t1, TInt.class) && !testType(t1, TUndef.class))
                || (!testType(t2, TInt.class) && !testType(t2, TUndef.class))) { //si un type d'un des fils est différent du type entre guillements
            throw new TypeException("Bad typing at Add: Integer expected");
        }
        return new TInt();
    }

    /**
     * Visite un nœud Sub, vérifie si ses deux fils sont de type Tint. Renvoie un Type Tint
     *
     * @param e expression étudiée
     * @return TInt
     * @throws Exception si son fils n'est pas un TInt
     */
    @Override
    public Type visit(Sub e) throws Exception {
        Type t1 = e.e1.accept(this);
        Type t2 = e.e2.accept(this);
        if (testType(t1, TUndef.class)) {
            t1 = t2;
        }
        if (testType(t2, TUndef.class)) {
            t2 = t1;
        }
        if ((!testType(t1, TInt.class) && !testType(t1, TUndef.class))
                || (!testType(t2, TInt.class) && !testType(t2, TUndef.class))) { //si un type d'un des fils est différent du type entre guillements
            throw new TypeException("Bad typing at Sub: Integer expected");
        }
        return new TInt();
    }

    /**
     * Visite un nœud FNeg, vérifie si son fils est de type TFloat
     *
     * @param e expression étudiée
     * @return TFloat
     * @throws Exception si son fils n'est pas un TInt
     */
    @Override
    public Type visit(FNeg e) throws Exception {
        Type t = e.e.accept(this);
        if (testType(t, TUndef.class)) {
            return new TFloat();
        }
        if (!testType(t, TFloat.class)) { //si le type du fils est différent du type entre guillements
            throw new TypeException("Bad typing at FNeg: Float expected");
        }
        return new TFloat();
    }

    /**
     * Visite un nœud Sub, vérifie si ses deux fils sont Type TFloat. Renvoie un Type TFloat
     *
     * @param e expression étudiée
     * @return TFloat
     * @throws Exception si ses fils ne sont pas du type TFloat
     */
    @Override
    public Type visit(FAdd e) throws Exception {
        Type t1 = e.e1.accept(this);
        Type t2 = e.e2.accept(this);
        if (testType(t1, TUndef.class)) {
            t1 = t2;
        }
        if (testType(t2, TUndef.class)) {
            t2 = t1;
        }
        if ((!testType(t1, TFloat.class) && !testType(t1, TUndef.class))
                || (!testType(t2, TFloat.class) && !testType(t2, TUndef.class))) { //si un type d'un des fils est différent du type entre guillements
            throw new TypeException("Bad typing at FAdd: Float expected");
        }
        return new TFloat();
    }

    /**
     * Visite un nœud FSub, vérifie si ses deux fils sont Type TFloat. Renvoie un Type TFloat
     *
     * @param e expression étudiée
     * @return TFloat
     * @throws Exception si ses fils ne sont pas du type TFloat
     */
    @Override
    public Type visit(FSub e) throws Exception {
        Type t1 = e.e1.accept(this);
        Type t2 = e.e2.accept(this);

        if (testType(t1, TUndef.class)) {
            t1 = t2;
        }
        if (testType(t2, TUndef.class)) {
            t2 = t1;
        }
        if ((!testType(t1, TFloat.class) && !testType(t1, TUndef.class))
                || (!testType(t2, TFloat.class) && !testType(t2, TUndef.class))) { //si un type d'un des fils est différent du type entre guillements
            throw new TypeException("Bad typing at FSub: Float expected");
        }
        return new TFloat();
    }

    /**
     * Visite un nœud FMul, vérifie si ses deux fils sont de Type TFloat ou TInt. Renvoie un Type TFloat
     *
     * @param e expression étudiée
     * @return TFloat
     * @throws Exception si ses fils ne sont pas du type TFloat ou TInt
     */
    @Override
    public Type visit(FMul e) throws Exception {
        Type t1 = e.e1.accept(this);
        Type t2 = e.e2.accept(this);
        if (testType(t1, TUndef.class)) {
            t1 = t2;
        }
        if (testType(t2, TUndef.class)) {
            t2 = t1;
        }

        if (testType(t1, TInt.class) && testType(t2, TInt.class)) { //si un type d'un des fils est différent du type entre guillements
            return t1;
        }

        if ((!testType(t1, TFloat.class) && !testType(t1, TInt.class) && !testType(t1, TUndef.class))
                || !testType(t2, TFloat.class) && !testType(t2, TInt.class) && !testType(t2, TUndef.class)) { //si un type d'un des fils est différent du type entre guillements
            throw new TypeException("Bad typing at FMul: Float or Int expected");
        }
        return new TFloat();
    }

    /**
     * Visite un nœud FDiv, vérifie si ses deux fils sont Type TFloat ou TInt. Renvoie un Type TFloat
     *
     * @param e expression étudiée
     * @return TFloat
     * @throws Exception si ses fils ne sont pas de type TFloat ou TInt
     */
    @Override
    public Type visit(FDiv e) throws Exception {
        Type t1 = e.e1.accept(this);
        Type t2 = e.e2.accept(this);
        if (testType(t1, TUndef.class)) {
            t1 = t2;
        }
        if (testType(t2, TUndef.class)) {
            t2 = t1;
        }
        if (!testType(t1, TFloat.class) && !testType(t1, TInt.class) && !testType(t1, TUndef.class)
                || !testType(t2, TFloat.class) && !testType(t2, TInt.class) && !testType(t2, TUndef.class)) { //si un type d'un des fils est différent du type entre guillements
            throw new TypeException("Bad typing at FDiv: Float or Int expected");
        }
        return new TFloat();
    }

    /**
     * Visite un nœud Eq, vérifie si ses deux fils sont du même Type et renvoie un TBool
     *
     * @param e expression étudiée
     * @return TBool
     * @throws Exception si ses fils ne sont pas du même type
     */
    @Override
    public Type visit(Eq e) throws Exception {
        Type t1 = e.e1.accept(this);
        Type t2 = e.e2.accept(this);
        if (testType(t1, TUndef.class)) {
            t1 = t2;
        }
        if (testType(t2, TUndef.class)) {
            t2 = t1;
        }
        if (!testType(t1, t2.getClass())) {
            throw new TypeException("Bad typing at Eq: same type expected");
        }
        return new TBool();
    }

    /**
     * Visite un nœud LE, vérifie si ses deux fils sont du même Type et renvoie un TBool
     *
     * @param e expression étudiée
     * @return TBool
     * @throws Exception si ses fils ne sont pas du même type
     */
    @Override
    public Type visit(LE e) throws Exception {
        Type t1 = e.e1.accept(this);
        Type t2 = e.e2.accept(this);
        if (testType(t1, TUndef.class)) {
            t1 = t2;
        }
        if (testType(t2, TUndef.class)) {
            t2 = t1;
        }
        if (!testType(t1, t2.getClass())
                && (!testType(t1, TFloat.class) || !testType(t1, TInt.class))) {
            throw new TypeException("Bad typing at LE: same type expected");
        }
        return new TBool();
    }

    /**
     * Visite un noeud If, vérifie si la première partie est une expression booléenne et que les deux autres fils sont du même type. Renvoie ce dernier.
     *
     * @param e expression étudiée
     * @return le type de ses deux fils
     * @throws Exception si le premier fils n'est pas une expression booléenne ou si ses autres fils ne sont pas du même type.
     */
    @Override
    public Type visit(If e) throws Exception {
        Type t1 = e.e1.accept(this);
        Type t2 = e.e2.accept(this);
        Type t3 = e.e3.accept(this);
        if (testType(t2, TUndef.class)) {
            t2 = t3;
        }
        if (testType(t3, TUndef.class)) {
            t3 = t2;
        }

        if (!testType(t1, TBool.class)) {
            throw new TypeException("Bad typing at IF: e1 is not boolean");
        }
        if (!testType(t2, t3.getClass())) {
            throw new TypeException("Bad typing at IF: e2 and e3 same type expected");
        }

        return t2;
    }

    /**
     * Visite un noeud let, vérifie si le nom de la variable existe déjà dans l'environnement de variable et renvoie le type de cette dernière. Si c'est le premier let la méthode renvoie TUnit.
     *
     * @param e expression étudiée
     * @return type de la variable
     * @throws Exception si le nom de la variable existe déjà
     */
    @Override
    public Type visit(Let e) throws Exception {
        num_let++;
        Type t1 = e.e1.accept(this);

        if (testType(t1, TArray.class)) {
            addId(listeArray, e.id, listeExp.get(e.e1));
        } else if (!testType(t1, TTuple.class)) {
            this.addId(listeVar, e.id, t1);
        } else {
            listeTuple.put(e.id.id, listeExpTuple.get(e.e1));
        }
        Type t2 = e.e2.accept(this);
        num_let--;
        if (num_let == 0) {
            return new TUnit();
        }
        return t2;
    }

    /**
     * Visite un noeud Var, vérifie si la variable existe dans l'environnement de variable. Renvoie son type.
     *
     * @param e expression étudiée
     * @return le type de la variable
     * @throws Exception si le nom de la variable n'existe pas
     */
    @Override
    public Type visit(Var e) throws Exception {

        if (!listeVar.containsKey(e.id.id) && !listeArray.containsKey(e.id.id) && !listeTuple.containsKey(e.id.id)) {
            throw new TypeException("Unknown variable " + e.id.id);
        }
        if (listeVar.containsKey(e.id.id)) {
            return listeVar.get(e.id.id);
        } else if (listeArray.containsKey(e.id.id)) {
            return new TArray();
        } else if (listeFun.containsKey(e.id.id)) {
            return listeFun.get(e.id.id);
        } else {
            return new TTuple();
        }
    }

    /**
     * Visite un noeud LetRec, vérifie si la fonction existe déjà dans l'environnement de fonction et renvoie le type de retour de la fonction.
     *
     * @param e expression étudiée
     * @return le type de retour de la fonction
     * @throws Exception si le nom de fonction existe déjà
     */
    @Override
    public Type visit(LetRec e) throws Exception {
        num_let++;
        if (listeFun.containsKey(e.fd.id)) {
            num_let--;
            if (num_let == 0) {
                return new TUnit();
            }
            return listeFun.get(e.fd.id);
        } else {
            TypeVisitor tv = new TypeVisitor(num_let);

            for (int i = 0; i < e.fd.args.size(); i++) {
                tv.addId(listeVar, e.fd.args.get(i), new TUndef());
            }
            addId(listeFun, e.fd.id, new TUndef());
            tv.listeVar.putAll(this.listeVar);
            tv.listeFun.putAll(this.listeFun);
            tv.listeArray.putAll(this.listeArray);

            Type trenvoi = e.fd.e.accept(tv);
            listeFun.remove(e.fd.id);
            addId(listeFun, e.fd.id, trenvoi);
            num_let--;
            if (num_let == 0) {
                return new TUnit();
            }
            return e.e.accept(this);
        }
    }

    /**
     * Visite un noeud App, vérifie que le nom de la fonction appelée existe dans l'environnement de fonction. Renvoie le type de retour de la fonction
     *
     * @param e expression étudiée
     * @return type de retour de la fonction
     * @throws Exception si la fonction n'existe pas
     */
    @Override
    public Type visit(App e) throws Exception {
        VarVisitor vv = new VarVisitor();
        Set<String> h = e.accept(vv);
        String rendu = "error";
        for (String s : h) {
            rendu = s;
            if (!listeFun.containsKey(rendu) && !listeVar.containsKey(rendu) &&
                    !listeArray.containsKey(rendu) && !listeTuple.containsKey(rendu)) {
                throw new TypeException("Unknow function " + rendu);
            }
        }
        if (listeVar.containsKey(rendu)) {
            return listeVar.get(rendu);
        }

        return listeFun.get(rendu);
    }

    /**
     * Visite un noeud Tuple, et ajoute le tuple dans l'environnement de Tuple (méthode non finie et non utilisée)
     *
     * @param e expression étudiée
     * @return type TTuple
     * @throws Exception si on veut ajouter des limites pour les types autorisés dans les tuples
     */
    @Override
    public Type visit(Tuple e) throws Exception {
        ArrayList<Type> lt = new ArrayList();
        for (Exp ex : e.es) {
            // ici si on veux limiter les type autoriser dans un tuple
            Type t = ex.accept(this);
            lt.add(t);
        }
        addExpTuple(e, lt);
        return new TTuple();
    }

    /**
     * Visite un noeud LetTuple, vérifie que la découpe du tuple est correct est déplace les éléments dans l'environnement de variable. Renvoie le type TTuple (pas implémenté)
     *
     * @param e expression étudiée
     * @return TTuple
     * @throws Exception si la découpe du tuple est mal faite
     */
    @Override
    public Type visit(LetTuple e) throws Exception {
        //TODO
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Visite un noeud Array, vérifie que la fonction est appelée avec les arguments bien typés et si les données insérées sont de type TInt ou TFloat
     *
     * @param e expression étudiée
     * @return type TArray
     * @throws Exception si le premier fils est bien un entier et si les données insérées sont des TInt ou TFloat
     */
    @Override
    public Type visit(Array e) throws Exception {

        Type t1 = e.e1.accept(this);
        Type t2 = e.e2.accept(this);
        if (testType(t1, TUndef.class)) {
            t1 = new TInt();
        }

        if (!testType(t1, TInt.class)) {
            throw new UnsupportedOperationException("Bad typing for Array for e1: integer expected");
        }

        if (!testType(t2, TFloat.class) && (!testType(t2, TInt.class)) && (!testType(t2, TUndef.class))) {
            throw new UnsupportedOperationException("Bad typing for Array parameter e2 : integer or Float expected");
        }
        addExpType(e, t2);
        return new TArray();

    }

    /**
     * Visite un noeud Get, vérifie le type des arguments et renvoie le type des données du tableau
     *
     * @param e expression étudiée
     * @return type des données du tableau (TInt ou TFloat)
     * @throws Exception si les arguments du noeud Get ne sont pas du bon type (le premier un TArray et le deuxième TInt)
     */
    @Override
    public Type visit(Get e) throws Exception {
        String id = "error";
        Type t1 = e.e1.accept(this);
        Type t2 = e.e2.accept(this);
        if (testType(t1, TUndef.class)) {
            t1 = new TArray();
            return new TUndef();
        }
        if (testType(t2, TUndef.class)) {
            t2 = new TInt();
            return new TUndef();
        }
        if (!testType(t1, TArray.class) || (!testType(t2, TInt.class))) {
            throw new UnsupportedOperationException("Bad typing for get parameter e1 : Array excepted and e2 : Integer expected");
        }
        VarVisitor vv = new VarVisitor();
        Set<String> h = e.e1.accept(vv);
        for (String s : h) {
            id = s;
        }
        return listeArray.get(id);
    }

    /**
     * Visite un noeud Put, et vérifie si les valeurs que l'on veut insérer sont du même type que les données du tableau
     *
     * @param e expression étudiée
     * @return TUnit
     * @throws Exception si l'élément à insérer n'est pas du même type que les élément du tableau
     */
    @Override
    public Type visit(Put e) throws Exception {
        String id = "error";
        Type t1 = e.e1.accept(this);
        Type t2 = e.e2.accept(this);
        Type t3 = e.e3.accept(this);
        VarVisitor vv = new VarVisitor();
        Set<String> h = e.e1.accept(vv);
        for (String s : h) {
            if (listeArray.containsKey(s)) {
                id = s;
            }
        }
        Type type = listeArray.get(id);
        if (!testType(t1, TArray.class) || !testType(t2, TInt.class) || !testType(t3, type.getClass())) {
            throw new UnsupportedOperationException("Bad typing for put :e2 and e3 same type expected");

        }
        return new TUnit();
    }

}
