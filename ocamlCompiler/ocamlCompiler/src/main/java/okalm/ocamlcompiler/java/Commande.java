package okalm.ocamlcompiler.java;

import java.io.FileReader;
import okalm.ocamlcompiler.java.ast.Exp;

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
            System.out.print("we need two parameter. use -h for help");
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
            Exp expr = parse(args);
            if (!bool_parse) {
                typechecking(expr);
                if (!bool_type) {
                    expr = frontend(expr);
                    if (!bool_ASML) {
                        expr = backend(expr);
                    }
                    output(expr);
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
        System.out.println("------ TypeChecking ------");
        TypeVisitor tv = new TypeVisitor();
        //TODO Decommenter cette ligne exp.accept(tv);
        System.out.println("Code type cheking is valid");
    }

    public static Exp frontend(Exp exp) {
        System.out.println("------ FrontEnd ------");

        /* TODO Décommenter pour tester K-Normalisation
        System.out.println("------ K-Normalisation ------");
        KNormVisitor kv = new KNormVisitor();
        //Exp kexp = exp.accept(kv);
        */
        PrintVisitor pv = new PrintVisitor();
        //kexp.accept(pv);


        /* TODO Décommenter pour tester A-Conversion
        System.out.println();
        System.out.println("------ A-Conversion ------");
        AlphaConversionVisitor acv = new AlphaConversionVisitor();
        Exp acvExp = exp.accept(acv);
        acvExp.accept(pv);
        return acvExp;
        */

        System.out.println();
        System.out.println("------ Reduction Let Expression ------");
        ReductionLetExpressionVisitor rlev = new ReductionLetExpressionVisitor();
        Exp rleExp = exp.accept(rlev);
        rleExp.accept(pv);
        return rleExp;
    }

    public static Exp backend(Exp exp) {
        System.out.println("backend");
        return exp;
    }

    public static void output(Exp exp) {
        System.out.println("output");
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
        System.out.println("BASIC MAIN:");
        Exp expression = (Exp) p.parse().value;
        assert (expression != null);
        System.out.println("------ AST ------");
        expression.accept(new PrintVisitor());
        System.out.println();

        System.out.println("------ Height of the AST ------");
        int height = Height.computeHeight(expression);
        System.out.println("using Height.computeHeight: " + height);

        ObjVisitor<Integer> v = new HeightVisitor();
        height = expression.accept(v);
        System.out.println("using HeightVisitor: " + height);
        return expression;
    }

}
