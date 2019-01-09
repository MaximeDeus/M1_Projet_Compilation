/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package okalm.asml;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author defoursr
 */
public class WorkList {

    public Exp_asml exp;
    public List<String> prec, suc; //précédent, successeur
    public ArrayList<String> gen,kill;

    public WorkList(Exp_asml exp) {
        this.exp = exp;
        this.prec = new ArrayList();
        this.suc = new ArrayList();
        this.gen = new ArrayList();
        this.kill = new ArrayList();
    }

}
