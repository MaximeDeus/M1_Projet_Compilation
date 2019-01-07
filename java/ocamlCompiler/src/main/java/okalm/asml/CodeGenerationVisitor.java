/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package okalm.asml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author liakopog
 */
public class CodeGenerationVisitor implements AsmlObjVisitor<String> {

    String[] registres = new String[13]; //Liste representant les registres r0-r12 utilisables par le programme
    String[] registresSpeciales = new String[3];
    ArrayList<String> variablesActives = new ArrayList<>();
    Map<String, String> mem = new HashMap(100);
    int frameCounter = 1;

    @Override
    public String visit(Ident e) {
        return e.ident;
    }

    @Override
    public String visit(Asmt e) {
        if (e.ident != null) {
            String[] regs;
            switch (e.e.getClass().toString()) {

                case "Add":
                    regs = choixRegistres(e.ident.toString() + "," + e.e.accept(this));
                    return ("ADD" + regs[0] + ", " + regs[1] + ", " + regs[2] + "\n");
                case "Sub":
                    regs = choixRegistres(e.ident.toString() + "," + e.e.accept(this));
                    return ("SUB " + regs[0] + ", " + regs[1] + ", " + regs[2] + "\n");

                case "":
                default:

                    break;

            }

        }
        return null;
    }

    @Override
    public String visit(Add e) {
        return (e.ident.accept(this) + "," + e.ioi.accept(this));
    }

    @Override
    public String visit(Sub e) {
        return (e.ident.accept(this) + ", " + e.ioi.accept(this));
    }

    private String[] choixRegistres(String e) {
        String[] args = e.split(",");
        String[] ret = new String[args.length];
        for (int i = 0; i < args.length; i++) {
            int r = 0;
            while (r < 13 && ret[i] == null) {
                if (args[i].equals(registres[r])) {//variable déja contenue dans un registre
                    ret[i] = "r" + r;

                }
            }

            r = 0;
            while (r < 13 && ret[i] == null) {
                if (registres[r] == null) {//Pas déja dans un registre, alors on cherche pour un registre vide
                    ret[i] = "r" + r;
                    remplirRegistre(r, args[i]);
                } else if (!(variablesActives.contains(registres[r]))) {//si la variable contenue dans registre r n'est pas utilisée plus tard
                    ret[i] = "r" + r;
                    remplirRegistre(r, args[i]);
                }
                r++;
            }
            r = 0;
            while (r < 13 && ret[i] == null) {
                if (variablesActives.contains(registres[r])) {//si la variable contenue dans registre r est utilisée plus tard, mais on n'a plus d'options
                    sauvegardeMemoire(r);
                    remplirRegistre(r, args[i]);
                    ret[i] = "r" + r;
                }
                r++;
            }
            try {                                             //Si le dernier argument est un int, alors on le parse et on le met dans un registre déjà decidé par la fonction
                int entier = Integer.parseInt(args[args.length - 1]);
                writer = writer + ("MOV " + ret[args.length - 1] + ", " + "#" + entier);
            } catch (NumberFormatException nfe) {
            }
            //TODO Ajouter le cas ou si un des deux arguments n'est pas actif dans la suite, on peut mettre le resultat dans son reg ex.: x=y+z -> R0=R0+R1 si y pas actif dans la suite
        }
        return args;
    }

    public void sauvegardeMemoire(int r) {
        writer = writer + ("STR " + "r" + r + "[fp+" + (frameCounter * 4) + "]" + "\n"); //On sauvegarde la variable qui etait dans le registre à utiliser dans la memoire...
        ArrayList<String> liste = mem.get(registres[r]);

        if (liste == null) {
            liste = new ArrayList<>();
            liste.add("[fp+" + (frameCounter * 4) + "]");           //...et on garde une trace de ce fait
            mem.put(registres[r], liste);
        }
        frameCounter++;
    }

    private void remplirRegistre(int r, String arg) {//Charger une variable depuis la memoire dans un registre
        ArrayList<String> liste = mem.get(arg);
        if (liste == null) {
            try {
                throw new Exception("Variable not found in memory, sorry");
            } catch (Exception ex) {
                Logger.getLogger(CodeGenerationVisitor.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            writer = writer + ("LDR " + "r" + r + liste.get(liste.size() - 1) + "\n"); //Recuperer la sauvegarde la plus recente, idealement il y a q'une de toute façon
        }
    }

    @Override
    public String visit(Call e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String visit(CallClo e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String visit(Fadd e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String visit(Fargs e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String visit(Fdiv e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String visit(Fmul e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String visit(Fneg e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String visit(Fsub e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String visit(Fundefs e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String visit(If e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String visit(Int e) {
        return Integer.toString(e.e);
    }

    @Override
    public String visit(Label e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String visit(Mem e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String visit(Neg e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String visit(New e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String visit(Nop e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String visit(ParenExp e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String visit(Tokens e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
