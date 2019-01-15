package okalm.backend;

import java.util.HashSet;
import java.util.Set;
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

    private String ident;
    private int labelNum;
    private Set<String> extFun;

    public printArmVisitor() {
        ident = "        ";
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
        switch (type) {
            case "Add":
                s += ident + "ADD\t" + e.ident.accept(this) + ", " + e.e.accept(this) + "\n";
                break;

            case "Sub":
                s += ident + "SUB\t" + e.ident.accept(this) + ", " + e.e.accept(this) + "\n";
                break;

            case "Int":
                s += ident + "MOV\t" + e.ident.accept(this) + ", " + e.e.accept(this) + "\n";
                break;

            case "Ident":
                s += ident + "MOV\t" + e.ident.accept(this) + ", " + e.e.accept(this) + "\n";
                break;

            case "Neg":
                s += "NEG TODO";
                break;

            case "Call":
                s += e.e.accept(this) + "\n" + ident + "MOV\t" + e.ident.accept(this) + ", R0\n";
                break;

            case "Asmt":
                s += e.e.accept(this) + "\n";
                break;

            default:
                s += "!" + type + " is not supported yet!";
                break;
        }
        s += ThenElse(e.asmt);
        return s;
    }

    @Override
    public String visit(Call e) {
        String s = "";
        int i = 0;
        for (Exp_asml elem : e.fargs) {
            s += ident + "MOV\tR" + i + ", " + elem.accept(this) + "\n";
            i++;
        }
        if (extFun.contains(e.label.accept(this))) {
            return s + ident + "BL\tmin_caml_" + e.label.accept(this) + " ";
        } else {
            return s + ident + "BL\t" + e.label.accept(this) + " ";
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
            s += ident + "LDMFD\tSP!, {lr}\n" + ident + "BX LR\n";
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
            s += ident + "NOP";
        } else {
            s += e.accept(this);
        }
        return s;
    }

    @Override
    public String visit(If e) {
        String s = "";
        s += ident + "CMP\t" + e.condasmt.accept(this) + "\n";    //comparaison des deux éléments
        s += e.condasmt.getClass().getSimpleName().equals("Eq") ? ident + "BEQ\t" : ident + "BLE\t";  //séléction du comparateur (EQ/LE)

        String t = getNewLabel(); // création du label du cas true
        s += t + "\n";
        s += ThenElse(e.elseasmt) + "\n";

        String end = getNewLabel(); //création du label de fin;
        s += ident + "B\t" + end + "\n"; // renvois de la phase else au label de fin
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
        String indent = e.ident.accept(this);
        String s = indent + " , " + indent;
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
