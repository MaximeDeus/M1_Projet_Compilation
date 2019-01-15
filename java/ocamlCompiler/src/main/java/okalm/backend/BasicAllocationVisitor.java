package okalm.backend;

import okalm.asml.*;
import okalm.tools.AsmlObjVisitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*Alloue des registres pour stocker chaque variable definie dans le programme.
 *On fait une
 * @author liakopog
 */
public class BasicAllocationVisitor implements AsmlObjVisitor<Exp_asml> {

    public String regList;
    public Map<String, Integer> reg;
    public int regNum;
    public int referenceFp;

    public BasicAllocationVisitor() {
        reg = new HashMap();
        regList = "";
        regNum = 4; //premier registre libre, les registres 4-10 inclus sont ceux qui peuvent etre utilisés sans restrictions pour stocker les variables

    }

    @Override
    public Exp_asml visit(Add e) {
        e.ident = e.ident.accept(this);
        e.identOrImm = e.identOrImm.accept(this);
        return e;
    }

    @Override
    public Exp_asml visit(Sub e) {
        e.ident = e.ident.accept(this);
        e.identOrImm = e.identOrImm.accept(this);
        return e;
    }

    @Override
    public Exp_asml visit(Asmt e) {
        e.ident = e.ident.accept(this);
        e.e = e.e.accept(this);
        e.asmt = e.asmt.accept(this);
        return e;
    }

    @Override
    public Exp_asml visit(Call e) {
        List<Exp_asml> l = new ArrayList();
        if (!e.fargs.isEmpty()) {
            e.fargs.forEach((element) -> {//Parcours de la liste d'arguments de cet appel de fonction
                l.add(element.accept(this));
            });
        }
        e.fargs = l;
        return e;
    }

    @Override
    public Exp_asml visit(CallClo e) {
        List<Exp_asml> l = new ArrayList();
        if (!e.fargs.isEmpty()) {
            e.fargs.forEach((element) -> {//Parcours de la liste d'arguments de cet appel de fonction

                l.add(element.accept(this));
            });
        }
        e.fargs = l;
        return e;
    }

    @Override
    public Exp_asml visit(Fargs e) {
        e.ident = e.ident.accept(this);
        return e;
    }

    @Override
    public Exp_asml visit(Fundefs e) {
        referenceFp = 0;        //Dans cette fonction, on a une pile vide
        e.asmt = e.asmt.accept(this);

        //Parcours de la liste des fonctions stockés dans ce fundef
        List<Exp_asml> fdefs = new ArrayList();
        if (!e.fundefs.isEmpty()) {
            e.fundefs.forEach((element) -> {

                fdefs.add(element.accept(this));
            });
        }
        e.fundefs = fdefs;//Puis on les remplace avec une liste qui contient les memes fonctions, mais avec des registres allouées

        //Parcours de la liste des arguments stockés dans ce fundef
        List<Exp_asml> fargs = new ArrayList();
        if (!e.formal_args.isEmpty()) {
            e.formal_args.forEach((element) -> {

                fargs.add(element.accept(this));
            });
        }
        e.formal_args = fargs;//Puis on les remplace avec une liste qui contient les memes arguments, mais allouées dans des régistres
        e.ident = e.ident.accept(this);

        e.label = e.label.accept(this);
        e.ident = e.ident.accept(this);
        return e;
    }

    /*
     *Alloue un registre à chaque variable qui en a pas encore
     */
    @Override
    public Exp_asml visit(Ident e) {
        Ident nouveauIdent;

        if (!reg.containsKey(e.ident)) {    //Si la variable n'est pas encore allouée
            if (regNum < 9) {              //S'il y a encore des registres libres, on les utilise
                reg.put(e.ident, regNum);       //On l'ajoute dans la liste des registres avec le prochain régistre libre comme déstination
//                regList += e.ident + "= R" + regNum + " | ";
                regNum++;
                nouveauIdent = new Ident("R" + reg.get(e.ident));
            } else {       //S' il ne restent plus de registres, on sauvegarde la variable dans la pile
                reg.put(e.ident, -1);    //Valeur -1 signifie que la variable existe, mais pas dans un registre
                nouveauIdent = new Ident("[fp" + "-" + 4 + 4 * referenceFp + "]");
                nouveauIdent.mem = true;//Cet attribut signifie que ce variable se trouve dans la mémoire
                referenceFp++;
            }
        } else {
            nouveauIdent = e;
        }
        return nouveauIdent;//On substitue ce noeud pour un nouveau noeud qui contient le régistre de destination comme nom
    }

    @Override
    public Exp_asml visit(If e) {
        e.condasmt = e.condasmt.accept(this);
        e.thenasmt = e.thenasmt.accept(this);
        e.elseasmt = e.elseasmt.accept(this);
        return e;
    }

    @Override
    public Exp_asml visit(Int e) {
        return e;   //Les int ne doivent pas se mettre dans les registres
    }

    @Override
    public Exp_asml visit(Label e) {
        return e;   //Les labels restent tels quels, pas de transformation à faire
    }

    @Override
    public Exp_asml visit(Mem e) {
        e.ident1 = e.ident1.accept(this);
        e.identOrImm = e.identOrImm.accept(this);
        e.ident2 = e.ident2.accept(this);
        return e;
    }

    @Override
    public Exp_asml visit(Neg e) {
        e.ident = e.ident.accept(this);
        return e;
    }

    @Override
    public Exp_asml visit(New e) {
        e.identOrImm = e.identOrImm.accept(this);
        return e;
    }

    @Override
    public Exp_asml visit(Nop e) {
        return e;
    }

    @Override
    public Exp_asml visit(ParenExp e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Exp_asml visit(Tokens e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Exp_asml visit(Fdiv e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Exp_asml visit(Fmul e
    ) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Exp_asml visit(Fneg e
    ) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Exp_asml visit(Fsub e
    ) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Exp_asml visit(Fadd e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Exp_asml visit(Eq e) {
        e.e1 = e.e1.accept(this);
        e.e2 = e.e2.accept(this);
        return e;
    }

    @Override
    public Exp_asml visit(LE e) {
        e.e1 = e.e1.accept(this);
        e.e2 = e.e2.accept(this);
        return e;
    }
}
