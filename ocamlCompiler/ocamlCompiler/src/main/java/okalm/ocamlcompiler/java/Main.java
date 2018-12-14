package okalm.ocamlcompiler.java;

import java_cup.runtime.*;
import java.io.*;
import java.util.*;

import okalm.ocamlcompiler.java.ast.*;

public class Main {
  static public void main(String argv[]) {    
    try {
      Parser p = new Parser(new Lexer(new FileReader(argv[0])));
      Exp expression = (Exp) p.parse().value;      
      assert (expression != null);

      System.out.println("------ AST ------");
      expression.accept(new PrintVisitor());
      System.out.println();

      System.out.println("------ Height of the AST ----");
      int height = Height.computeHeight(expression);
      System.out.println("using Height.computeHeight: " + height);

      ObjVisitor<Integer> v = new HeightVisitor();
      height = expression.accept(v);
      System.out.println("using HeightVisitor: " + height);
      
      System.out.println("------ TypeChecking ----");
      TypeVisitor tv = new TypeVisitor();
      expression.accept(tv);
      System.out.println("Code type cheking is valid");

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

