package okalm.ocamlcompiler.java.type;

public abstract class Type {
    private static int x = 0;
    static Type gen() {
        return new TVar("?" + x++);
    }
    
}