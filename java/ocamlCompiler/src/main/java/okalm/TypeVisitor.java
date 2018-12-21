package okalm;

import java.util.HashMap; 
import java.util.Map;
import java.util.Set;
import okalm.ast.*;
import okalm.type.*;

/**
 *
 * @author defoursr DiardJul
 */
public class TypeVisitor implements ObjErrorVisitor<Type> {

    private Map<String, Type> listeVar;
    private Map<String, Type> listeFun;
    private Map<String, Type> listeArray;
    private Map<String, Type> listeTuple;
    private Map<Exp, Type> listeExp;

    public TypeVisitor() {
        listeVar = new HashMap();
        listeFun = new HashMap();
        listeArray = new HashMap();
        listeTuple = new HashMap();
        listeExp = new HashMap();
        listeFun.put("print_int", new TUnit());
        listeFun.put("truncate", new TInt());

    }

    /**
     * Ajoute un Id à l'environnement l actuel
     *
     * @param  l name of the map
     * @param id new Id to add to the environement
     * @param t type to associate with the Id
     * @throws TypeException if the Id has already been declared before
     */
    private void addId(Map<String, Type> l, Id id, Type t) throws TypeException {
        if(l==listeFun && l.containsKey(id.id)){
            listeFun.remove(id.id);
        }
        if (!l.isEmpty()&&l.containsKey(id.id)) {
            throw new TypeException("Declaration error "+l+" : variable " + id.toString() + " declared twice or more");
        } else {
            l.put(id.id, t);
        }
    }
     private void addExpType(Exp exp, Type t){
         listeExp.put(exp, t);
     }

    private Boolean testType(Type t, Class s) {
        return t.getClass().getSimpleName().equals(s.getSimpleName());
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
    public Type visit(okalm.ast.Float e) {
        return new TFloat();
    }

    @Override
    public Type visit(Not e) throws Exception {
        Type t = e.e.accept(this);
        if (this.testType(t, TUndef.class)) {
            return new TBool();
        }
        if (!testType(t, TBool.class)) { //si le type du fils est différent du type entre guillements
            throw new TypeException("Bad typing at Not: boolean expected");
        }
        return new TBool();
    }

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
        if (!testType(t, TInt.class)) { //si le type du fils est différent du type entre guillements
            throw new TypeException("Bad typing at Neg: Integer expected");
        }
        return new TInt();
    }

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

    @Override
    public Type visit(Let e) throws Exception {
        // Let (x : id_type) = exrp1 in expr2
        // expr1 must be of the type id_type, so we generate 
        // GenEquations(env, expr1, id_type) 
        // expr2 must be of type "type", but in a new environment where
        // x is of type id_type, so we generate
        // GenEquations(env + (id -> id_type), expr2, type)
        Type t1 = e.e1.accept(this);

        if (testType(t1, TArray.class)){
            
            addId(listeArray, e.id, listeExp.get(e.e1));
        }else if (!testType(t1, TTuple.class)){
            this.addId(listeVar,e.id, t1);
        }else{
            this.addId(listeTuple,e.id, t1);
        }
        Type t2 = e.e2.accept(this);
        
        return t2;
    }

    @Override
    public Type visit(Var e) throws Exception {
        
        if (!listeVar.containsKey(e.id.id)&&!listeArray.containsKey(e.id.id)) {
            throw new TypeException("Unknow variable " + e.id.id);
        }
        if(listeVar.containsKey(e.id.id)){
            return listeVar.get(e.id.id);
        }else if(listeArray.containsKey(e.id.id)){
            return new TArray();
        }else if (listeFun.containsKey(e.id.id)){
            return listeFun.get(e.id.id);
        }else{
            return listeTuple.get(e.id.id);
        }
    }

    @Override
    public Type visit(LetRec e) throws Exception {
        
        if (listeFun.containsKey(e.fd.id)){
            return listeFun.get(e.fd.id);
        }else{
            TypeVisitor tv = new TypeVisitor();

            for (int i = 0; i < e.fd.args.size(); i++) {
                tv.addId(listeVar,e.fd.args.get(i), new TUndef());
            }
            addId(listeFun,e.fd.id, new TUndef());
            tv.listeVar.putAll(this.listeVar);
            tv.listeFun.putAll(this.listeFun);
            tv.listeArray.putAll(this.listeArray);
            
            Type trenvoi = e.fd.e.accept(tv);
            listeFun.remove(e.fd.id);
            addId(listeFun,e.fd.id, trenvoi);
            return e.e.accept(this);
        }
    }

    @Override
    public Type visit(App e) throws Exception {
        VarVisitor vv = new VarVisitor();
        Set<String> h = e.accept(vv);
        String rendu = "error";

        for (String s : h) {
            rendu = s;
            if (!listeFun.containsKey(rendu) && !listeVar.containsKey(rendu)) {
                throw new TypeException("Unknow function " + rendu);
            }
        }
        if (listeVar.containsKey(rendu)) {
            return listeVar.get(rendu);
        }
        
        return listeFun.get(rendu);
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

    @Override
    public Type visit(Get e) throws Exception {
        
        String id="error";
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

    @Override
    public Type visit(Put e) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
