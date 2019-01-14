/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package okalm.backend;

import java.util.ArrayList;
import java.util.List;
import okalm.asml.Exp_asml;

/**
 *
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