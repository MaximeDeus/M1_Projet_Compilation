package okalm.backend;

import java.util.ArrayList;

/*
Definit les variables in et out de chaque instruction ou bloc basique
 */
public class VarInOut {

    public ArrayList<String> in;
    public ArrayList<String> out;

    public VarInOut() {
        in = new ArrayList<>();
        out = new ArrayList<>();
    }

    public VarInOut(ArrayList<String> in) {
        this.in = in;
        out = new ArrayList<>();
    }
}
