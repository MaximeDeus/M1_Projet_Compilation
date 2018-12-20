/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package okalm.asml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author liakopog
 */
public class CodeGenerationVisitor implements AsmlErrorVisitor<String> {

    File fichierARM;
    FileWriter writer;
    ArrayList<String> memoire = new ArrayList<>();
    String[] registres = new String[13]; //Liste representant les registres r0-r12 utilisables par le programme
    String[] registresSpeciales = new String[3];
    ArrayList<String> variablesActives = new ArrayList<>();
    int frameCounter = 0;

    public CodeGenerationVisitor() throws IOException {
        fichierARM = new File("./ARMcode.s");
        writer = new FileWriter(fichierARM);
    }

    @Override
    public String visit(Add e) throws Exception {

        return (e.ident.accept(this) + "," + e.ioi.accept(this));
    }

    @Override
    public String visit(Sub e) throws Exception {
        return (e.ident.accept(this) + ", " + e.ioi.accept(this));

    }

    @Override
    public String visit(Asmt e) throws Exception {
        if (e.ident != null) {
            switch (e.e.getClass().toString()) {

                case "Add":

                    String[] regs = findRegisters(e.ident.toString() + "," + e.e.accept(this));

                    writer.write("ADD" + regs[0] + regs[1] + regs[2]);
            }
        }

    }

    @Override
    public String visit(Ident e) throws Exception {
        return e.ident;
    }

    //Prend trois variables et retourne les registres pour une instruction à trois adresses
    private String[] findRegisters(String e) throws Exception {
        String[] args = e.split(",");
        String[] ret = new String[3];
        for (int i = 0; i < 3; i++) {
            int r = 0;
            while (r < 13 && ret[i] == null) {
                if (args[i].equals(registres[r])) {
                    ret[i] = "r" + r;
                } else if (registres[r] == null) {//Pas déja dans un registre, alors on cherche pour un registre vide
                    ret[i] = "r" + r;
                } else if (!(variablesActives.contains(registres[r]))) {//si la variable contenue dans registre r n'est pas utilisée plus tard
                    ret[i] = "r" + r;
                }
                r++;
            }
            while (r < 13 && ret[i] == null) {
                if (variablesActives.contains(registres[r])) {//si la variable contenue dans registre r est utilisée plus tard, mais on n'a plus d'options
                    writer.write("STR " + "r" + r + "[fp+" + frameCounter + "]");
                    frameCounter++;
                    ret[i] = "r" + r;
                }
                r++;
            }
            //TODO Ajouter le cas ou si un des deux arguments n'est pas actif dans la suite, on peut mettre le resultat dans son reg ex.: x=y+z -> R0=R0+R1 si y pas actif dans la suite
        }
        return args;
    }

}
