/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package okalm.backend;

import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author liakopog
 */
public class Graphe {

    HashMap<String, HashSet<String>> graphe;

    public Graphe() {
        graphe = new HashMap<>();

    }

    public void ajouterNoeud(String n) {
        if (graphe.containsKey(n)) {
            try {
                throw new Exception("Noeud existe déja");
            } catch (Exception ex) {
                Logger.getLogger(Graphe.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            graphe.put(n, new HashSet<>());
        }
    }

    public void ajouterArete(String noeud1, String noeud2) {
        graphe.get(noeud1).add(noeud2);
        graphe.get(noeud2).add(noeud1);
    }

    public void supprimerNoeud(String n) {
        graphe.get(n).forEach((t) -> {
            graphe.get(t).remove(n);
        });
        graphe.remove(n);
    }

    public void supprimerArete(String noeud1, String noeud2) {
        graphe.get(noeud1).remove(noeud2);
        graphe.get(noeud2).remove(noeud1);
    }

    public void effondrementNoeuds(String noeud1, String noeud2) {
        graphe.get(noeud1).addAll(graphe.get(noeud2));
        supprimerNoeud(noeud2);
    }

}
