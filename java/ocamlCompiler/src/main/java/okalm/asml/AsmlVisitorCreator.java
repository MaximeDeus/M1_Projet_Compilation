/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package okalm.ocamlcompiler.java.asml;

/**
 *
 * @author liakopog
 */
public class AsmlVisitorCreator {

    Fundefs racine;

    public AsmlVisitorCreator(Fundefs racine) {
        this.racine = racine;
    }

    public void allouerRegistres() {
        racine.accept(new AllSpillAllocationVisitor());
    }

    public void createVisitors(Fundefs f) {
        f.accept(new AllSpillAllocationVisitor());
        if (f.fundefs != null) {
            createVisitors(f.fundefs);
        }
    }
}
