package okalm.ocamlcompiler.java;

import java_cup.runtime.*;
import java.io.*;
import java.util.*;

import okalm.ocamlcompiler.java.ast.*;

public class Main {
  static public void main(String argv[]) {    
    try {
      Commande.option(argv);
    } catch (Exception e) {
      e.printStackTrace();
        System.out.println("1");
    }
  }
}

