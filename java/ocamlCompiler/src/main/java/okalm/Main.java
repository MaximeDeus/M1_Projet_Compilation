package okalm;


public class Main {
  static public void main(String argv[]) {    
    try {
      Commande.option(argv);
      System.exit(0);
    } catch (Exception e) {
      e.printStackTrace(); //affichage des erreurs
      System.err.println(e.getMessage());
      System.exit(1);
    }
  }
}

