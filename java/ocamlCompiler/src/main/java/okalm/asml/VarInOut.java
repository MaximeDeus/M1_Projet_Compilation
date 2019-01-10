package okalm.asml;

import java.util.ArrayList;

public class VarInOut {

    ArrayList<String> in, out;

    public VarInOut() {
        in = new ArrayList<>();
        out = new ArrayList<>();
    }

    public VarInOut(ArrayList<String> in) {
        this.in = in;
        out = new ArrayList<>();
    }
}
