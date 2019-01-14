package okalm.backend;

import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/** 
 * Classe réprésentant le graphe d'interférence des variables du programme
 * @author liakopog
 */
public class Graphe {

    HashMap<String, HashSet<String>> graphe;

    public Graphe() {
        graphe = new HashMap<>();

    }

    /*Ajoute un nouveau noeud dans le graphe
    *@param String n
     */
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

    /*Ajoute une arete dans le graphe, qui signifie que les variables réprésentées par les deux noeuds sont actives en meme temps quelque part dans le programme
     */
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
