package okalm.ast;

import java.util.List;

import okalm.tools.ObjErrorVisitor;
import okalm.tools.ObjVisitor;
import okalm.tools.Visitor;

public class App extends Exp {

    public final Exp e;
    public final List<Exp> es;

    /**
     *
     * @param e Var avec id= nom de la fonction appelée
     * @param es Paramètre de l'appel de la fonction
     */
    public App(Exp e, List<Exp> es) {
        this.e = e;
        this.es = es;
    }

    public <E> E accept(ObjVisitor<E> v) {
        return v.visit(this);
    }

    public void accept(Visitor v) {
        v.visit(this);
    }

    public <E> E accept(ObjErrorVisitor<E> v) throws Exception {
        return v.visit(this);
    }
}
