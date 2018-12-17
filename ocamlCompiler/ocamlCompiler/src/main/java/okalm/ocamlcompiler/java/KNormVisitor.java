package okalm.ocamlcompiler.java;
import okalm.ocamlcompiler.java.ast.*;
import okalm.ocamlcompiler.java.ast.Float;
import okalm.ocamlcompiler.java.type.*;

public class KNormVisitor implements ObjVisitor<Exp> {

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

    public Let visit(Eq e){
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
    }

    public Let visit(LE e){
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
    }

    public If visit(If e){
        Exp e1 = e.e1.accept(this);
        Exp e2 = e.e2.accept(this);
        Exp e3 = e.e3.accept(this);
        return new If(e1, e2, e3);
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
        /**TODO à terminer
        Exp e1 = e.e.accept(this);
        Exp e2 = e.e2.accept(this);
        Id new_var1 = Id.gen();
        Type new_type1 = Type.gen();
        Id new_var2 = Id.gen();
        Type new_type2 = Type.gen();
        Let res = new Let(new_var1, new_type1, e1,
                new Let(new_var2, new_type2, e2,
                        new LE (new Var(new_var1), new Var(new_var2))));
        return res;
    }
         */
        return null;
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


