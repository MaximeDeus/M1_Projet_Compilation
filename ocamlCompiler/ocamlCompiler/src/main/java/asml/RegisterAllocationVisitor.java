package asml;

import java.util.ArrayList;
import okalm.ocamlcompiler.java.ast.*;

/**
 *
 * @author liakopog
 */
public class RegisterAllocationVisitor {

    Var[] registres = new Var[9]; //Liste representant les registres r4-r12 utilisables par le programme
    ArrayList<Var> mem = new ArrayList<>(); //Liste des variables allouees dans la memoire

    public void allocationBasique(Var noeudVar) {

        if (!mem.contains(noeudVar)) {
            mem.add(noeudVar);
        }

    }

}
