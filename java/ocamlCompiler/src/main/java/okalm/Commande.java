package okalm;

import java.io.FileReader;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.File;
import okalm.asml.BasicAllocationVisitor;
import okalm.asml.CodeGenerationVisitor;
import okalm.asml.Exp_asml;
import okalm.ast.Exp;

/**
 *
 * @author diardjul
 */
public class Commande {

    static boolean bool_type = false;
    static boolean bool_frontend = false;
    static boolean bool_backend = false;
    static boolean bool_parse = false;
    static boolean bool_help = false;
    static boolean bool_version = false;
    static boolean bool_ASML = false;
    static boolean bool_base = false;
    static boolean bool_erreur = false;
    static boolean bool_outputfile = false;
    static String nom_fichier;

    /**
     * option d'argument: -h: HELP -o: OUTPUT FILE -v: DISPLAY VERSION -t: TYPE
     * CHECK ONLY -p: PARSE ONLY -asml: OUTPUT ASML -base: BASIC MAIN
     *
     * @param args tableau d'argument
     * @throws Exception
     */
    public static void option(String args[]) throws Exception {

        if (args.length < 2) {
            throw new IllegalArgumentException("More arguments needed, use -h for more informations.");
        } else {

            for (int i = 1; i < args.length; i++) {

                switch (args[i]) {

                    case "-o":
                        //output file
                        bool_outputfile = true;
                        if (args.length > i + 1) {
                            String[] s = args[i + 1].split(".");
                            if (s.length == 2 && s[1].equals("s")) {
                                nom_fichier = args[i + 1];
                                i++;
                            }
                        }
                        break;

                    case "-h":
                        //help
                        bool_help = true;
                        break;

                    case "-v":
                        //display version
                        bool_version = true;
                        break;

                    case "-t":
                        //type checking only
                        bool_type = true;
                        break;

                    case "-p":
                        //parse only
                        bool_parse = true;

                        break;

                    case "-asml":
                        //output ASML
                        bool_ASML = true;
                        break;

                    case "-base":
                        bool_base = true;
                        break;

                    default:
                        bool_erreur = true;
                        break;
                }
            }
            compute(args);
        }

    }

    public static void compute(String args[]) throws Exception {

        if (bool_erreur) {
            error();
        } else if (bool_version) {
            version();
        } else if (bool_help) {
            help();
        } else {
            Exp exp = parse(args);
            if (!bool_parse) {
                typechecking(exp);
                if (!bool_type) {
                    Exp_asml exp_asml = frontend(exp);
                    output(exp_asml);
                    if (!bool_ASML) {
                        exp_asml = backend(exp_asml);
                        outputARM(exp_asml);
                    }
                }

            }
        }
    }

    public static void help() {
        System.out.println("HELP:");
        System.out.println("format: exec mincaml_programme -commande");
        System.out.println("-o : output file");
        System.out.println("-h : help");
        System.out.println("-v : display version");
        System.out.println("-t : type checking only");
        System.out.println("-p : parse only");
        System.out.println("-asml : ASML file");
        System.out.println("-base : basic main");
    }

    public static void typechecking(Exp exp) throws Exception {
       /* System.out.println("------ TypeChecking ------");*/
        TypeVisitor tv = new TypeVisitor();
        exp.accept(tv);
        /*System.out.println("Code type cheking is valid");*/
    }

    public static Exp_asml frontend(Exp exp) {
        //System.out.println("_____ FrontEnd _____");
        System.out.println("---parsed code: ");
        PrintVisitor pv = new PrintVisitor();
        exp.accept(pv);
        //K-norm
        //System.out.println("\n\n------ K-Normalisation ------");
        KNormVisitor kv = new KNormVisitor();
        exp = exp.accept(kv);
        //exp.accept(pv); //affichage K-normalisation

        //a-convers
        //System.out.println("\n------ A-Conversion ------");
        AlphaConversionVisitor acv = new AlphaConversionVisitor();
        exp = exp.accept(acv);
        //exp.accept(pv); //affichage A-Conversion

        //reduction let
        //System.out.println("\n------ Reduction Let Expression ------");
        ReductionLetExpressionVisitor rlev = new ReductionLetExpressionVisitor();
        exp = exp.accept(rlev);
        //exp.accept(pv); //affichage let expression

        System.out.println("\n------ Closure ------");
        ClosureVisitor cv = new ClosureVisitor();
        exp = exp.accept(cv);
        cv.functionsToString(); //affichage des fonctions après closure
        System.out.print("\n");
        exp.accept(pv); //affichage code après closure

        //System.out.println("\n------ FrontEnd to BackEnd ------");
        FrontToEndVisitor ftev = new FrontToEndVisitor();
        Exp_asml exp_asml = exp.accept(ftev);
        exp_asml = ftev.wrapCode(exp_asml, cv.listeFun);
        return exp_asml;

    }

    public static Exp_asml backend(Exp_asml exp) {
        //System.out.println("_____ Backend _____");
        BasicAllocationVisitor bav = new BasicAllocationVisitor();
        exp = exp.accept(bav);
        System.out.println("LIste des registres: "+bav.regList);
        return exp;
    }

    public static void outputARM(Exp_asml exp) {
        System.out.println("\n\n---ARM code: ");
        printArmVisitor pav = new printArmVisitor();
        System.out.println(exp.accept(pav));
        
        write_in_file ("Output/result.s", exp, pav);

    }

    public static void output(Exp_asml exp) {
        System.out.println("\n\n---ASML code");
        printAsmlVisitor pav = new printAsmlVisitor(true);
        System.out.println(exp.accept(pav));
        
        write_in_file ("Output/result.asml", exp, pav);
    }

    public static void output(Exp exp) {
        System.out.println("\noutput backend");
    }

    public static void error() {
        System.out.println("ERROR:");
        System.out.println("bad option use -h for help.");
    }

    public static void version() {
        System.out.println("current version:1.0");

    }

    public static Exp parse(String args[]) throws Exception {

        Parser p = new Parser(new Lexer(new FileReader(args[0])));
        /*System.out.println("BASIC MAIN:");*/
        Exp expression = (Exp) p.parse().value;
        assert (expression != null);
        /*System.out.println("------ AST ------");
        expression.accept(new PrintVisitor());
        System.out.println();*/

       /* System.out.println("------ Height of the AST ------");
        int height = Height.computeHeight(expression);
        System.out.println("using Height.computeHeight: " + height);*/

        /*ObjVisitor<Integer> v = new HeightVisitor();
        height = expression.accept(v);
        System.out.println("using HeightVisitor: " + height);*/
        return expression;
    }
    
    public static void write_in_file  (String chemin, Exp_asml exp,printAsmlVisitor pav){
        
        final File fichier =new File(chemin); 
        try {
            fichier.createNewFile();
            final FileWriter writer = new FileWriter(fichier);
            try {
                writer.write(exp.accept(pav));
            } finally {
                writer.close();
            }
        } catch (Exception e) {
            System.out.println("Impossible de creer le fichier");
        }
    }
    
    public static void write_in_file  (String chemin, Exp_asml exp,printArmVisitor pav){
        
        final File fichier =new File(chemin); 
        try {
            fichier.createNewFile();
            final FileWriter writer = new FileWriter(fichier);
            try {
                writer.write(exp.accept(pav));
            } finally {
                writer.close();
            }
        } catch (Exception e) {
            System.out.println("Impossible de creer le fichier");
        }
    }

}
