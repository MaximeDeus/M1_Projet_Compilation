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

        if (type.equals("Add") || type.equals("Sub")) {
            Add add = (Add) e.e;
            Ident arg1 = (Ident) add.ident;
            if (arg1.mem) {
                s += indent + "LDR\tR10, " + arg1.accept(this) + "\n";
                end += indent + "STR\tR10, " + arg1.accept(this) + "\n";
                arg1.ident = "R10";
            }
            if (add.identOrImm.getClass().getSimpleName().equals("Ident")) {
                Ident arg2 = (Ident) add.identOrImm;
                if (arg2.mem) {
                    s += indent + "LDR\tR9, " + arg2.accept(this) + "\n";
                    end += indent + "STR\t R9, " + arg2.accept(this) + "\n";
                    arg2.ident = "R9";
                }
            }
        } else if (type.equals("Ident")) {
            Ident arg = (Ident) e.e;
            if (arg.mem) {
                s += indent + "LDR\t R10, " + arg.accept(this) + "\n";
                end += indent + "STR\t R10, " + arg.accept(this) + "\n";
                arg.ident = "R10";
            }
        }
        Ident id = (Ident) e.ident;
        if (id.mem) {
            s += indent + "LDR\t R12, " + e.ident.accept(this) + "\n";
            end += indent + "STR\t R12, " + e.ident.accept(this) + "\n";
            id.ident = "R12";
        }

        switch (type) {
            case "Add":
                s += indent + "ADD\t" + e.ident.accept(this) + ", " + e.e.accept(this) + "\n";
                break;

            case "Sub":
                s += indent + "SUB\t" + e.ident.accept(this) + ", " + e.e.accept(this) + "\n";
                break;

            case "Int":
                s += indent + "MOV\t" + e.ident.accept(this) + ", " + e.e.accept(this) + "\n";
                break;

            case "Ident":
                s += indent + "MOV\t" + e.ident.accept(this) + ", " + e.e.accept(this) + "\n";
                break;

            case "Neg":
                s += "NEG TODO";
                break;

            case "Call":
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
        s += ThenElse(e.asmt);
        return s;
    }

    @Override
    public String visit(Call e) {
        String s = "";
        int i = 0;
        for (Exp_asml elem : e.fargs) {
            if (elem.getClass().getSimpleName().equals("Ident")) {
                Ident id = (Ident) elem;
                if (id.mem) {

                    s += indent + "LDR\tR" + i + ", " + elem.accept(this) + "\n";
                    id.ident = "R" + i;
                } else {
                    s += indent + "MOV\tR" + i + ", " + elem.accept(this) + "\n";
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

        Eq eq = (Eq) e.condasmt;
        Ident arg1 = (Ident) eq.e1;
        if (arg1.mem) {
            s += indent + "LDR\tR10, " + arg1.accept(this) + "\n";
            arg1.ident = "R10";
        }
        Ident arg2 = (Ident) eq.e2;
        if (arg2.mem) {
            s += indent + "LDR\tR9, " + arg2.accept(this) + "\n";
            arg2.ident = "R9";
        }

        s += indent + "CMP\t" + e.condasmt.accept(this) + "\n";    //comparaison des deux éléments
        s += e.condasmt.getClass().getSimpleName().equals("Eq") ? indent + "BEQ\t" : indent + "BLE\t";  //séléction du comparateur (EQ/LE)

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
