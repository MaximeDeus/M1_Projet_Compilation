package okalm;


public class Main {
  static public Integer main(String argv[]) {    
    try {
      Commande.option(argv);
      return 0; //success
    } catch (Exception e) {
      //e.printStackTrace();
      System.err.println(e.getMessage());
      return 1;
    }
  }
}

