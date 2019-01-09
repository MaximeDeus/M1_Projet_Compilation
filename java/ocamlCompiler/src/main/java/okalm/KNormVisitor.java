package okalm;
import okalm.ast.*;
import okalm.ast.Float;
import okalm.type.Type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class KNormVisitor implements ObjVisitor<Exp> {

    //Attributs utilisés pour la méthode App
    private LinkedList<Exp> liste_arguments = new LinkedList<>(); //Liste qui contient les arguments k-normalisés
    private HashMap<App,Integer> nb_argument_fonction = new HashMap<>(); //Cette map associe une fonction à son nombre de paramètre

    public Unit visit(Unit e) {
        return e;
    }

    public Bool visit(Bool e) {
        return e;
    }

    public Int visit(Int e) {
        return e;
    }

    public Float visit(Float e) {
        return e;
    }

    public Let visit(Not e) {
        Exp e1 = e.e.accept(this);
        Id new_var = Id.gen();
        Type new_type = Type.gen();
        return new Let(new_var, new_type, e1, new Not(new Var(new_var))) ;
    }

    public Let visit(Neg e) {
        Exp e1 = e.e.accept(this);
        Id new_var = Id.gen();
        Type new_type = Type.gen();
        return new Let(new_var, new_type, e1, new Neg(new Var(new_var))) ;
    }

    public Let visit(Add e) {
        Exp e1 = e.e1.accept(this);
        Exp e2 = e.e2.accept(this);
        Id new_var1 = Id.gen();
        Type new_type1 = Type.gen();
        Id new_var2 = Id.gen();
        Type new_type2 = Type.gen();
        Let res = new Let(new_var1, new_type1, e1,
                  new Let(new_var2, new_type2, e2,
                  new Add(new Var(new_var1), new Var(new_var2))));
        return res;
    }
	
    public Let visit(Sub e) {
        Exp e1 = e.e1.accept(this);
        Exp e2 = e.e2.accept(this);
        Id new_var1 = Id.gen();
        Type new_type1 = Type.gen();
        Id new_var2 = Id.gen();
        Type new_type2 = Type.gen();
        Let res = new Let(new_var1, new_type1, e1,
                  new Let(new_var2, new_type2, e2,
                  new Sub(new Var(new_var1), new Var(new_var2))));

        return res;
    }

    public Exp visit(FNeg e){ 
        Exp e1 = e.e.accept(this);
        Id new_var = Id.gen();
        Type new_type = Type.gen();
        Let res = new Let (new_var,new_type,e1, new FNeg(new Var(new_var)));
        return res;
    }

    public Let visit(FAdd e){
        Exp e1 = e.e1.accept(this);
        Exp e2 = e.e2.accept(this);
        Id new_var1 = Id.gen();
        Type new_type1 = Type.gen();
        Id new_var2 = Id.gen();
        Type new_type2 = Type.gen();
        Let res = new Let(new_var1, new_type1, e1,
                new Let(new_var2, new_type2, e2,
                        new FAdd(new Var(new_var1), new Var(new_var2))));
        return res;
    }

    public Let visit(FSub e){
        Exp e1 = e.e1.accept(this);
        Exp e2 = e.e2.accept(this);
        Id new_var1 = Id.gen();
        Type new_type1 = Type.gen();
        Id new_var2 = Id.gen();
        Type new_type2 = Type.gen();
        Let res = new Let(new_var1, new_type1, e1,
                new Let(new_var2, new_type2, e2,
                        new FSub (new Var(new_var1), new Var(new_var2))));
        return res;
    }

    public Let visit(FMul e) {
        Exp e1 = e.e1.accept(this);
        Exp e2 = e.e2.accept(this);
        Id new_var1 = Id.gen();
        Type new_type1 = Type.gen();
        Id new_var2 = Id.gen();
        Type new_type2 = Type.gen();
        Let res = new Let(new_var1, new_type1, e1,
                new Let(new_var2, new_type2, e2,
                        new FMul (new Var(new_var1), new Var(new_var2))));
        return res;
    }

    public Let visit(FDiv e){
        Exp e1 = e.e1.accept(this);
        Exp e2 = e.e2.accept(this);
        Id new_var1 = Id.gen();
        Type new_type1 = Type.gen();
        Id new_var2 = Id.gen();
        Type new_type2 = Type.gen();
        Let res = new Let(new_var1, new_type1, e1,
                new Let(new_var2, new_type2, e2,
                        new FDiv (new Var(new_var1), new Var(new_var2))));

        return res;
    }

    public Exp visit(Eq e){ //Renvoie Let initialement
        /**
        Exp e1 = e.e1.accept(this);
        Exp e2 = e.e2.accept(this);
        Id new_var1 = Id.gen();
        Type new_type1 = Type.gen();
        Id new_var2 = Id.gen();
        Type new_type2 = Type.gen();
        Let res = new Let(new_var1, new_type1, e1,
                new Let(new_var2, new_type2, e2,
                        new Eq (new Var(new_var1), new Var(new_var2))));
        return res;
         */
        return e;
    }

    public Exp visit(LE e){ //Renvoie Let initialement
        /*
        Exp e1 = e.e1.accept(this);
        Exp e2 = e.e2.accept(this);
        Id new_var1 = Id.gen();
        Type new_type1 = Type.gen();
        Id new_var2 = Id.gen();
        Type new_type2 = Type.gen();
        Let res = new Let(new_var1, new_type1, e1,
                  new Let(new_var2, new_type2, e2,
                        new LE (new Var(new_var1), new Var(new_var2))));
        return res;
        */
        return e;
    }

    public Let visit(If e){ //Renvoie If initialement
        /**
        Exp e1 = e.e1.accept(this);
        Exp e2 = e.e2.accept(this);
        Exp e3 = e.e3.accept(this);
        return new If(e1, e2, e3);
         */

        Id id1 = Id.gen();
        Type type1 = Type.gen();
        Var var1 = new Var (id1);

        Id id2 = Id.gen();
        Type type2 = Type.gen();
        Var var2 = new Var (id2);

        if (e.e1 instanceof Not){
            Not not = (Not) e.e1;
            if (not.e instanceof LE){
                LE le = (LE) not.e;
                return new Let (id1,type1,le.e1,
                       new Let (id2,type2,le.e2,
                       new If  (new Not(new LE(var1,var2)),e.e2.accept(this),e.e3.accept(this))));

            }
            else{
                Eq eq = (Eq) not.e;
                return new Let (id1,type1,eq.e1,
                       new Let (id2,type2,eq.e2,
                       new If  (new Eq(var1,var2),e.e3.accept(this),e.e2.accept(this))));
            }

        }

        if (e.e1 instanceof LE){
            LE le = (LE) e.e1;
            return new Let (id1,type1,le.e1,
                   new Let (id2,type2,le.e2,
                   new If  (new LE(var1,var2),e.e2.accept(this),e.e3.accept(this))));

        }
        else{
            Eq eq = (Eq) e.e1;
            return new Let (id1,type1,eq.e1,
                   new Let (id2,type2,eq.e2,
                   new If  (new Eq(var1,var2),e.e2.accept(this),e.e3.accept(this))));
        }

    }

    public Let visit(Let e) {
        Exp e1 = e.e1.accept(this);
        Exp e2 = e.e2.accept(this);
        Id new_var1 = Id.gen();
        Type new_type1 = Type.gen();
        Id new_var2 = Id.gen();
        Type new_type2 = Type.gen();
        Let res = new Let(e.id,e.t, e1, e2);
        return res;
    }

    public Var visit(Var e){
        return e;
    }

    public Exp visit(LetRec e){
        //TODO à tester
        Exp e1 = e.e.accept(this);
        Exp e2 = e.fd.e.accept(this);
        FunDef fd = new FunDef(e.fd.id,e.fd.type,e.fd.args,e2);
        LetRec res = new LetRec(fd, e1);
        return res;
    }

    public Exp visit(App e){


        //TODO v1 qui fonctionne (version basique)
       /** Id new_id = Id.gen();
        Type new_type = Type.gen();
        List<Exp> new_es = new LinkedList<>();
        new_es.add(new Var(new_id));

        Let new_let = new Let (new_id,new_type,e.es.get(0),new App(e.e,new_es));

        return new_let;
        */

       /**
        //TODO v2 qui fonctionne (version appels imbriqués)
        Id new_id = Id.gen();
        Type new_type = Type.gen();
        List<Exp> new_es = new LinkedList<>();
        new_es.add(new Var(new_id));

        Let new_let = new Let (new_id,new_type,e.es.get(0).accept(this),new App(e.e,new_es));


        return new_let;
        */

        Id new_id = Id.gen();
        Type new_type = Type.gen();

        if (!nb_argument_fonction.containsKey(e)){ //Permet de stocker nombre arguments de la fonction
            nb_argument_fonction.put(e,e.es.size());
        }

        if (e.es.size() > 0) { //S'il y a encore des arguments à k-normaliser
                liste_arguments.add(new Var(new_id)); //On stocke dans la liste les nouveaux arguments (v0 v1 etc.)
                Exp argument = e.es.get(0); //On prend le premier argument de la fonction ( f a b -> on prend a)
                e.es.remove(0); //Que l'on retire aussi de l'appel de fonction initial (f a b devient f b)
                //On effectue une application partielle de la fonction
                return new Let(new_id, new_type, argument.accept(this), e.accept(this));
        }
        else { //lorsqu'il n'y a plus d'argument à traiter
            List<Exp> tmp = new LinkedList<>(); //On construit liste arguments pour chaque fonction
            int i;
            int nombre_arguments = nb_argument_fonction.get(e); //Nombre d'arguments de la fonction traitée
            int position_argument = liste_arguments.size() - nombre_arguments; //Permet de conserver l'ordre
            for (i = 0 ; i < nombre_arguments ; i++){
                tmp.add(liste_arguments.get(position_argument));
                liste_arguments.remove(position_argument);
            }
            return new App (e.e,tmp); //Fin de l'algo (le fils droit = app initial modifié)
        }

    }


    public Tuple visit(Tuple e){
       //TO DO
        return null;
    }

    public LetTuple visit(LetTuple e){
        //TO DO
        return null;
    }

    public Let visit(Array e){
        //TO DO
        return null;
   }

    public Let visit(Get e){
        //TO DO
        return null; 
    }

    public Let visit(Put e){
        Exp e1 = e.e1.accept(this);
        Exp e2 = e.e2.accept(this);
        Exp e3 = e.e3.accept(this);

        Id new_var1 = Id.gen();
        Type new_type1 = Type.gen();
        Id new_var2 = Id.gen();
        Type new_type2 = Type.gen();
        Id new_var3 = Id.gen();
        Type new_type3 = Type.gen();

        Let res = 
        new Let(new_var1, new_type1, e1,
          new Let(new_var2, new_type2, e2,
              new Let(new_var3, new_type3, e3,
                new Put(new Var(new_var1), new Var(new_var2), new Var(new_var3)))));
        return res;
    }
}

