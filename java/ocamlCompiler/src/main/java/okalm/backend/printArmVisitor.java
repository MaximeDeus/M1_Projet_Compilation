package okalm.backend;

import okalm.asml.*;
import okalm.tools.AsmlObjVisitor;

import java.util.HashSet;
import java.util.Set;

/**
 * Génére du code ARM à partir d'un arbre asml
 *
 * @author defoursr
 */
public class printArmVisitor implements AsmlObjVisitor<String> {

    private String indent;
    private int labelNum;
    private Set<String> extFun;

    public printArmVisitor() {
        indent = "        ";
        labelNum = 0;
        extFun = new HashSet();
        extFun.add("print_int");
        extFun.add("print_newline");
        extFun.add("print_string");
        extFun.add("exit");
        extFun.add("hello_world");
        extFun.add("print_char");
    }

    public String getNewLabel() {
        labelNum++;
        return "label" + labelNum;
    }

    @Override
    public String visit(Add e) {
        return e.ident.accept(this) + " , " + e.identOrImm.accept(this);
    }

    @Override
    public String visit(Sub e) {
        return e.ident.accept(this) + " , " + e.identOrImm.accept(this);
    }

    @Override
    public String visit(Asmt e) {
        String type = e.e.getClass().getSimpleName();

        String s = "";
        String end = "";

        //Si l'expression contenue dans l'arbre asmt est une variable, et en plus elle se trouve dans la mémoire, on la charge dans le registre 10
        Ident id = (Ident) e.ident;
        String adresseIdent = e.ident.accept(this);
        if (id.mem) {
            s += indent + "LDR\tR12, " + e.ident.accept(this) + "\n";
            end += indent + "STR\tR12, " + e.ident.accept(this) + "\n";
            id.ident = "R12";
        }

        switch (type) {
            case "Add":
                Add add = (Add) e.e;
                String adresseAdd1 = null;
                String adresseAdd2 = null;
                boolean add2Modifie = false;
                Ident add1 = (Ident) add.ident;
                if (add1.mem) {
                    s += indent + "LDR\tR10, " + add1.accept(this) + "\n";      //On charge la variable dans un régistre
                    adresseAdd1 = add.ident.accept(this);
                    add1.ident = "R10";
                }
                if (add.identOrImm.getClass().getSimpleName().equals("Ident")) {    //On verifie qu'il s'agit d'une variable, et non pas d'un Int (ou une valeur immédiate plus généralement)
                    Ident add2 = (Ident) add.identOrImm;
                    if (add2.mem) {
                        s += indent + "LDR\tR9, " + add2.accept(this) + "\n";
                        adresseAdd2 = add.identOrImm.accept(this);
                        add2.ident = "R9";
                        add2Modifie = true;         //L'adresse/emplacement de la variable identOrImm a été modifié, nous devons la reinitialiser après son utilisation, dans le cas où ça sera necéssaire dans la suite de l'utiliser
                    }
                }

                s += indent + "ADD\t" + e.ident.accept(this) + ", " + e.e.accept(this) + "\n";      //Instruction codée dans l'expression e

                if (add1.mem) {
                    add1.ident = adresseAdd1;       //reinitialisation de l'adresse originale de la variable ident
                }
                if (add2Modifie) {
                    Ident add2 = (Ident) add.identOrImm;
                    add2.ident = adresseAdd2;       //reinitialisation de l'adresse originale de la variable identOrImm
                }
                break;

            case "Sub":
                Sub sub = (Sub) e.e;
                String adresseSub1 = null;
                String adresseSub2 = null;
                boolean sub2Modifie = false;
                Ident sub1 = (Ident) sub.ident;
                if (sub1.mem) {
                    s += indent + "LDR\tR10, " + sub1.accept(this) + "\n";      //On charge la variable dans un régistre
                    adresseSub1 = sub.ident.accept(this);
                    sub1.ident = "R10";
                }
                if (sub.identOrImm.getClass().getSimpleName().equals("Ident")) {    //On verifie qu'il s'agit d'une variable, et non pas d'un Int (ou une valeur immédiate plus généralement)
                    Ident sub2 = (Ident) sub.identOrImm;
                    if (sub2.mem) {
                        s += indent + "LDR\tR9, " + sub2.accept(this) + "\n";
                        adresseSub2 = sub.identOrImm.accept(this);
                        sub2.ident = "R9";
                        sub2Modifie = true; //L'adresse/emplacement de la variable identOrImm a été modifié, nous devons la reinitialiser après son utilisation, dans le cas où ça sera necéssaire dans la suite de l'utiliser
                    }
                }
                s += indent + "SUB\t" + e.ident.accept(this) + ", " + e.e.accept(this) + "\n";      //Instruction codée dans l'expression e

                if (sub1.mem) {
                    sub1.ident = adresseSub1;       //reinitialisation de l'adresse originale de la variable ident
                }
                if (sub2Modifie) {
                    Ident sub2 = (Ident) sub.identOrImm;
                    sub2.ident = adresseSub2;       //reinitialisation de l'adresse originale de la variable identOrImm
                }
                break;

            case "Int":
                s += indent + "MOV\t" + e.ident.accept(this) + ", " + e.e.accept(this) + "\n";
                break;

            case "Ident":

                Ident arg = (Ident) e.e;
                String adresseArg = e.e.accept(this);       //Sauvegarde de son adresse originale pour la restituer après
                if (arg.mem) {
                    s += indent + "LDR\tR10, " + arg.accept(this) + "\n";
                    arg.ident = "R10";//affecte la variable e dans le registre 10. Attention, après il faut restaurer son adresse memoire, sinon on ne va plus pouvoir le récuperer
                }
                s += indent + "MOV\t"+ e.ident.accept(this) + ", " + e.e.accept(this) + "\n";
                arg.ident = adresseArg;     //restitution de l'adresse originale de la variable e

                break;

            case "Neg":
                s += "NEG TODO";
                break;

            case "Call":
                //On génére le code de la commande call, et on récupére son valeur de retour dans Ident
                s += e.e.accept(this) + "\n" + indent + "MOV\t" + e.ident.accept(this) + ", R0\n";
                break;

            case "Asmt":
                s += e.e.accept(this) + "\n";
                break;

            default:
                s += "!" + type + " is not supported yet!";
                break;
        }
        s += end;
        if(!e.asmt.getClass().getSimpleName().equals("Ident")){
        s += ThenElse(e.asmt);}
        id.ident = adresseIdent;        //On remet dans l'Ident son propre adresse, soit qu'il soit dans un registre ou dans la mémoire
        return s;
    }

    @Override
    public String visit(Call e) {
        String s = "";
        int i = 0;
        for (Exp_asml elem : e.fargs) {     //Parcours de la liste des arguments formels de la fundef
            if (elem.getClass().getSimpleName().equals("Ident")) {      //Si l'argument est une variable
                Ident id = (Ident) elem;
                if (id.mem) {               //Si on a marqué qu'il réside en mémoire

                    s += indent + "LDR\tR" + i + ", " + elem.accept(this) + "\n";       //On charge la variable depuis la mémoire
                } else {
                    s += indent + "MOV\tR" + i + ", " + elem.accept(this) + "\n";       //Sinon, la variable se trouve dans un régistre, alors on effectue un move(MOV)
                }
                i++;
            }
        }
        if (extFun.contains(e.label.accept(this))) {
            return s + indent + "BL\tmin_caml_" + e.label.accept(this) + " ";
        } else {
            return s + indent + "BL\t" + e.label.accept(this) + " ";
        }
    }

    @Override
    public String visit(CallClo e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String visit(Fundefs e) {
        String s = "";
        for (Exp_asml f : e.fundefs) {
            s += f.accept(this);
        }
        Boolean isMain = e.label.accept(this).equals("_");
        if (isMain) {
            s += "main:\n";
        } else {
            s += e.label + s + ":\n";
        }
        s += e.asmt.accept(this) + "\n";
        if (!isMain) {
            s += indent + "LDMFD\tSP!, {lr}\n" + indent + "BX LR\n";
        }
        return s;
    }

    @Override
    public String visit(Ident e) {
        return e.ident;
    }

    private String ThenElse(Exp_asml e) {
        String s = "";
        if (e.getClass().getSimpleName().equals("Int")) {
            s += indent + "NOP";
        } else {
            s += e.accept(this);
        }
        return s;
    }

    @Override
    public String visit(If e) {
        String s = "";

        //On regarde s'il faut récuperer une variable depuis la memoire avant d'evaluer la condition du if
        Eq eq = (Eq) e.condasmt;
        String adresseArg1 = null, adresseArg2 = null;

        Ident arg1 = (Ident) eq.e1;     //On caste la premiere expression pour pouvoir accéder dans ses champs
        if (arg1.mem) {
            s += indent + "LDR\tR10, " + arg1.accept(this) + "\n";  //On charge dans le registre 10 depuis son adresse en memoire
            adresseArg1 = arg1.ident;
            arg1.ident = "R10";
        }
        Ident arg2 = (Ident) eq.e2;     //On caste la deuxième expression pour pouvoir accéder dans ses champs
        if (arg2.mem) {
            s += indent + "LDR\tR9, " + arg2.accept(this) + "\n";   //On charge dans le registre 10 depuis son adresse en memoire
            adresseArg2 = arg2.ident;
            arg2.ident = "R9";
        }

        s += indent + "CMP\t" + e.condasmt.accept(this) + "\n";    //comparaison des deux éléments
        s += e.condasmt.getClass().getSimpleName().equals("Eq") ? indent + "BEQ\t" : indent + "BLE\t";  //séléction du comparateur (EQ/LE)

        if (arg1.mem) {
            arg1.ident = adresseArg1;   //reinitialisation de l'adresse originale de la variable e1
        }
        if (arg2.mem) {
            arg2.ident = adresseArg2;   //reinitialisation de l'adresse originale de la variable e2
        }

        String t = getNewLabel(); // création du label du cas true
        s += t + "\n";
        s += ThenElse(e.elseasmt) + "\n";

        String end = getNewLabel(); //création du label de fin;
        s += indent + "B\t" + end + "\n"; // renvois de la phase else au label de fin
        s += t + ":\n"; //label true
        s += ThenElse(e.thenasmt) + "\n";
        s += end + ":";

        return s;
    }

    @Override
    public String visit(Int e) {
        return "#" + e.e;
    }

    @Override
    public String visit(Label e) {
        return e.ident;
    }

    @Override
    public String visit(Mem e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String visit(Neg e) {
        String ident = e.ident.accept(this);
        String s = ident + " , " + ident;
        return s;
    }

    @Override
    public String visit(New e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String visit(Nop e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String visit(ParenExp e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String visit(Tokens e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String visit(Fadd e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String visit(Fargs e) {
        return e.ident.accept(this);
    }

    @Override
    public String visit(Fdiv e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String visit(Fmul e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String visit(Fneg e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String visit(Fsub e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String visit(Eq e) {
        return e.e1.accept(this) + " , " + e.e2.accept(this);
    }

    @Override
    public String visit(LE e) {
        return e.e1.accept(this) + " , " + e.e2.accept(this);
    }

}
