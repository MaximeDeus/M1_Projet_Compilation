package okalm.asml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.sun.org.apache.bcel.internal.generic.NEW;

/**
 *
 * @author liakopog
 */
public class RechercheVariablesActives {
	HashMap<String, WorkList> wlList;
	HashMap<Exp_asml, ArrayList<String>> listevars = new HashMap<Exp_asml, ArrayList<String>>(); // Liste de tout bloc et ses variables actives
	HashMap<WorkList, VarInOut> mapInOut = new HashMap<>(); // Liste de tout bloc et ses in et out

	public RechercheVariablesActives(HashMap<String, WorkList> wlList) {

		this.wlList = wlList;
	}

	public HashMap<Exp_asml, ArrayList<String>> rechercher() {
		for (WorkList wlracine : wlList.values()) {

			parcoursRecursifBlocInitialisation(wlracine); // Initialisation du In de chaque bloc avec son Gen

		}

		return listevars;

	}

	public void parcoursRecursifBloc(WorkList w) {

	}

	public void parcoursRecursifBlocInitialisation(WorkList w) {
		VarInOut inOut = new VarInOut(w.gen);
		mapInOut.put(w, inOut);
		for (String wSucc : w.suc) {
			parcoursRecursifBlocInitialisation(wlList.get(wSucc));
		}
	}

}
