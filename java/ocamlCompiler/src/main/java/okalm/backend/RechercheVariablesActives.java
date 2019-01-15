package okalm.backend;

import okalm.asml.Exp_asml;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author liakopog
 */
public class RechercheVariablesActives {

    HashMap<String, WorkList> wlList;
    HashMap<Exp_asml, ArrayList<String>> listevars = new HashMap<>(); // Liste de tout bloc et ses variables actives
    HashMap<WorkList, VarInOut> mapInOut = new HashMap<>(); // Liste de tout bloc et ses in et out

    public RechercheVariablesActives(HashMap<String, WorkList> wlList) {

        this.wlList = wlList;
    }

    public HashMap<Exp_asml, ArrayList<String>> rechercher() {
        for (WorkList wlracine : wlList.values()) {       //On fait un parcours recursif en commençant par la definition de la fonction qui constitue le bloc de code qu'on veut evaluer

            HashMap<WorkList, VarInOut> mapPrecedenteIteration = mapInOut;
            parcoursRecursifBlocInitialisation(wlracine); // Initialisation du In de chaque bloc avec son Gen
            while (!(mapPrecedenteIteration.equals(mapInOut))) {    //Tant que on a pas trouvé un point fixe, on répéte le parcours recursif des instructions
                mapPrecedenteIteration = mapInOut;
                parcoursRecursifBloc(wlracine);
            }
        }

        return listevars;

    }

    /*
    *Fonction qui parcourt un bloc et ses successeurs pour trouver quelles variables sont actives
     */
    public void parcoursRecursifBloc(WorkList w) {
        VarInOut inOut = mapInOut.get(w);

        for (String wSuccesseur : w.suc) {
            VarInOut temp = new VarInOut();
            inOut.out.addAll(mapInOut.get(wlList.get(wSuccesseur)).in);

            temp.out = inOut.out;
            temp.out.removeAll(w.kill);
            temp.out.addAll(w.gen);

            inOut.in.addAll(temp.out);

        }
        mapInOut.put(w, inOut);
        for (String wSuccesseur : w.suc) {
            parcoursRecursifBloc(wlList.get(wSuccesseur));
        }

    }

    /*
     *Initialise chaque bloc et ses successeurs recursivement
     */
    public void parcoursRecursifBlocInitialisation(WorkList w) {
        VarInOut inOut = new VarInOut(w.gen);
        mapInOut.put(w, inOut);
        for (String wSuccesseur : w.suc) {
            parcoursRecursifBlocInitialisation(wlList.get(wSuccesseur));
        }
    }

}
