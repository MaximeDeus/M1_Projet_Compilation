package okalm;


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

