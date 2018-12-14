package okalm.ocamlcompiler.java;


import java.io.FileReader;


public class Main {
  static public void main(String argv[]) {
      //String arg[]= argument(argv);
    try {
      Commande.option(argv);
    } catch (Exception e) {
      e.printStackTrace();
        System.out.println("1");
    }
  }
}

