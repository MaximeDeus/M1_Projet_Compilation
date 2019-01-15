package okalm;

import okalm.asml.Exp_asml;
import okalm.ast.Exp;
import okalm.backend.BasicAllocationVisitor;
import okalm.backend.printArmVisitor;
import okalm.backend.printAsmlVisitor;
import okalm.frontend.*;
import okalm.typechecking.TypeVisitor;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * @author diardjul
 */
public class Commande {

    private static final double VERSION = 1.0;

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
    static String nom_fichier_output;
    static String nom_fichier_mincalm;

    /**
     * options d'argument: -h: HELP -o: OUTPUT FILE -v: DISPLAY VERSION -t: TYPE
     * CHECK ONLY -p: PARSE ONLY -asml: OUTPUT ASML -base: BASIC MAIN
     *
     * @param args tableau d'argument
     * @throws Exception
     */
    public static void option(String args[]) throws Exception {
        int i = 0;
        if (args.length < 2) {
            throw new IllegalArgumentException("More arguments needed, use -h for more informations.");
        } else {

            for (i = 0; i < args.length; i++) {
                if (args[i].endsWith(".ml")) {
                    nom_fichier_mincalm = args[i];
                } else {
                    switch (args[i]) {
                        case "-o":
                            //output file
                            bool_outputfile = true;
                            if (args.length >= i + 1) {
                                if (args[i + 1].endsWith(".s") || args[i + 1].endsWith(".asml")) {
                                    nom_fichier_output = args[i + 1];
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
            }
            compute();
        }

    }

    public static void compute() throws Exception {

        if (bool_erreur) {
            throw new IllegalArgumentException("Bad option, use -h for more informations.");
        } else if (bool_version) {
            version();
        } else if (bool_help) {
            help();
        } else {
            Exp exp = parse(nom_fichier_mincalm);
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

    /**
     * effectue le typechecking
     *
     * @param exp arbre correctement parsé
     * @throws Exception problème de typage
     */
    public static void typechecking(Exp exp) throws Exception {
        /* System.out.println("------ TypeChecking ------");*/
        TypeVisitor tv = new TypeVisitor(0);
        exp.accept(tv);
        /*System.out.println("Code type cheking is valid");*/
    }

    /**
     * execute les étapes du Frontend et convertit l'arbre AST en arbre ASML
     *
     * @param exp arbre correctement parsé et typé
     * @return arbre ASML
     * @throws Exception
     */
    public static Exp_asml frontend(Exp exp) throws Exception {
        //System.out.println("_____ FrontEnd _____");
        //System.out.println("---parsed code--- ");
        //PrintVisitor pv = new PrintVisitor();
        //exp.accept(pv);
        //System.out.println("\n\n------ K-Normalisation ------");
        KNormVisitor kv = new KNormVisitor();
        exp = exp.accept(kv);
        //exp.accept(pv); //affichage K-normalisation

        //System.out.println("\n------ A-Conversion ------");
        AlphaConversionVisitor acv = new AlphaConversionVisitor();
        exp = exp.accept(acv);
        //exp.accept(pv); //affichage A-Conversion

        // System.out.println("\n------ Reduction Let Expression ------");
        ReductionLetExpressionVisitor rlev = new ReductionLetExpressionVisitor();
        exp = exp.accept(rlev);
        //exp.accept(pv); //affichage let expression

        //System.out.println("\n------ Closure ------");
        ClosureVisitor cv = new ClosureVisitor();
        exp = exp.accept(cv);
        //System.out.println(cv.functionsToString()); //affichage des fonctions après closure
        //exp.accept(pv); //affichage code après closure

        //System.out.println("\n------ FrontEnd to BackEnd ------");
        FrontToEndVisitor ftev = new FrontToEndVisitor();
        Exp_asml exp_asml = exp.accept(ftev);
        exp_asml = ftev.wrapCode(exp_asml, cv.listeFun);
        return exp_asml;

    }

    /**
     * effectue les étapes du backend
     *
     * @param exp arbre ASML
     * @return arbre ASML traité
     * @throws Exception problème dans le backend
     */
    public static Exp_asml backend(Exp_asml exp) throws Exception {
        //System.out.println("_____ Backend _____");
        BasicAllocationVisitor bav = new BasicAllocationVisitor();
        exp = exp.accept(bav);
        //System.out.println("Liste des registres: " + bav.regList);
        return exp;
    }

    /**
     * convertit un arbre ASML en ARM et l'écrit dans un fichier
     *
     * @param exp arbre ASML
     * @throws Exception problème de traduction ou d'écriture
     */
    public static void outputARM(Exp_asml exp) throws Exception {
        //System.out.println("\n\n---ARM code: ");
        printArmVisitor pav = new printArmVisitor();
        //System.out.println(exp.accept(pav));
        writeInFile("output/result.s", exp.accept(pav));
        if (bool_outputfile) {
            writeInFile(nom_fichier_output, exp.accept(pav));
        }

    }

    /**
     * couvertit un arbre ASML en code ASML et l'écrit dans un fichier
     *
     * @param exp arbre ASML
     * @throws Exception Exception problème de traduction ou d'écrire
     */
    public static void output(Exp_asml exp) throws Exception {
        //System.out.println("\n\n---ASML code");
        printAsmlVisitor pav = new printAsmlVisitor();
        //System.out.println(exp.accept(pav));
        writeInFile("output/result.asml", exp.accept(pav));
        if (bool_outputfile) {
            writeInFile(nom_fichier_output, exp.accept(pav));
        }
    }

    /**
     * affiche la version du programme
     */
    public static void version() {
        System.out.println(Commande.VERSION);

    }

    /**
     * convertit un programme Mincaml en Arbre parsé
     *
     * @param s programme
     * @return arbre d'Exp
     * @throws Exception problème dans le parseur
     */
    public static Exp parse(String s) throws Exception {

        Parser p = new Parser(new Lexer(new FileReader(s)));
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

    /**
     * écrit une chaîne de caractères dans un fichier donné
     *
     * @param chemin le chemin et le nom du fichier
     * @param code   chaîne de caractère
     * @throws Exception en cas d'erreur d'écriture
     */
    public static void writeInFile(String chemin, String code) throws Exception {
        final File fichier = new File(chemin);
        //System.out.println("file: " + chemin);
        fichier.createNewFile();
        final FileWriter writer = new FileWriter(fichier);
        try {
            writer.write(code);
        } finally {
            writer.close();
        }

    }

}
