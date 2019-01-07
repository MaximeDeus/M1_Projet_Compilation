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
    public Exp_asml prec,suc,exp; //précédent, successeur, expression
    public List<String> gen,kill;

    
    public WorkList(Exp_asml exp) {
        this.exp = exp;
        this.gen = new ArrayList();
        this.kill = new ArrayList();
    }
    
    public WorkList(Exp_asml exp,Exp_asml prec) {
        this.exp = exp;
        this.prec = prec;
        this.gen = new ArrayList();
        this.kill = new ArrayList();
    }
    
     public WorkList(Exp_asml exp,Exp_asml prec, Exp_asml suc) {
        this.exp = exp;
        this.prec = prec;
        this.suc = suc;
        this.gen = new ArrayList();
        this.kill = new ArrayList();
    }



}