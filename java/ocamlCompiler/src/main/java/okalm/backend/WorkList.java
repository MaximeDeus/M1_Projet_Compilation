package okalm.backend;

import okalm.asml.Exp_asml;

import java.util.ArrayList;
import java.util.List;

/**
 * Permet de preserver des informations pertinentes pour chaque instruction du
 * programme, qu'on utilise pour faire le graphe d'interférence des variables
 * @author defoursr
 */
public class WorkList {

    public Exp_asml exp;
    public List<String> prec, suc; //précédent, successeur
    public ArrayList<String> gen, kill;
    public boolean isMove;

    public WorkList(Exp_asml exp) {
        this.exp = exp;
        this.prec = new ArrayList();
        this.suc = new ArrayList();
        this.gen = new ArrayList();
        this.kill = new ArrayList();
        this.isMove = false;
    }

}
