package okalm.backend;

import okalm.asml.*;
import okalm.tools.AsmlObjVisitor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liakopog
 */
public class VariableLivenessVisitor implements AsmlObjVisitor<Map<String, String>> {

    Map<String, WorkList> wlList;

    public VariableLivenessVisitor() {
        wlList = new HashMap<>();
        //TODO: mettre le worklist parser ici

    }

    @Override
    public Map<String, String> visit(Add e) {
        Map m = new HashMap();

        //Les variables de la partie droite sont renvoyées avec l'etiquette Gen
        m.putAll(e.ident.accept(this));
        m.putAll(e.identOrImm.accept(this));
        m.forEach((t, u) -> {
            u = "Gen";
        });

        return m;
    }

    @Override
    public Map<String, String> visit(Sub e) {

        Map m = new HashMap();

        //Les variables de la partie droite sont renvoyées avec l'etiquette Gen
        m.putAll(e.ident.accept(this));
        m.putAll(e.identOrImm.accept(this));
        m.forEach((t, u) -> {
            u = "Gen";
        });

        return m;
    }

    @Override
    public Map<String, String> visit(Asmt e) {
        Map m = new HashMap();
        WorkList wl = new WorkList(e);

        String type = e.e.getClass().getSimpleName();
        switch (type) {                 //On regarde si l'instruction de base du asmt est un move(MOV)

            case "Int":
                wl.isMove = true;
                break;

            case "Ident":
                wl.isMove = true;
                break;
        }
        wl.suc.add(e.asmt.toString());
        wl.kill.add(e.ident.toString());
        e.e.accept(this).forEach((t, u) -> {
            if (u.equals("Gen")) {
                wl.gen.add(t);
            }
        });
        WorkList wlTemp = wlList.get(e.asmt.toString());
        wlTemp.prec.add(e.toString());          //Le noeud courant est le noeud precedent du noeud "contenu" dans son champ asmt
        wlList.put(e.toString(), wl);          //Ajout de la worklist de cette instruction dans la liste

        return null;
    }

    @Override
    public Map<String, String> visit(Call e) {
        WorkList wl = new WorkList(e);

        e.fargs.forEach((farg) -> {             //les arguments de l'appel doivent etre actifs, alors on les met dans Gen
            wl.gen.add(farg.toString());
        });
        wlList.put(e.toString(), wl);

        return null;
    }

    @Override
    public Map<String, String> visit(CallClo e) {
        WorkList wl = new WorkList(e);

        e.fargs.forEach((farg) -> {             //les arguments de l'appel doivent etre actifs, alors on les met dans Gen
            wl.gen.add(farg.toString());
        });
        wlList.put(e.toString(), wl);

        return null;
    }

    @Override
    public Map<String, String> visit(Fargs e) {
        Map m = new HashMap();
        m.put(e.ident.accept(this), "");
        return m;
    }

    @Override
    public Map<String, String> visit(Fundefs e) {

        WorkList wl = new WorkList(e);

        WorkList wlTemp = wlList.get(e.asmt.toString());
        wlTemp.prec.add(e.toString());          //Le noeud courant est le noeud precedent du noeud "contenu" dans son champ asmt
        wlList.put(e.toString(), wl);
        return null;
    }

    @Override
    public Map<String, String> visit(Ident e) {
        Map m = new HashMap();
        m.put(e.toString(), "");
        return m;
    }

    @Override
    public Map<String, String> visit(If e) {
        WorkList wl = new WorkList(e);
        Map m = new HashMap();

        //On ajoute les deux chemins possibles d'execution dans les successeurs de cette instruction
        wl.suc.add(e.thenasmt.toString());
        wl.suc.add(e.elseasmt.toString());

        //Le noeud courant If est le predecesseurs des intructions qui sont dans les arbres then et else
        WorkList wlTemp = wlList.get(e.thenasmt.toString());
        wlTemp.prec.add(e.toString());
        wlTemp = wlList.get(e.elseasmt.toString());
        wlTemp.prec.add(e.toString());

        e.condasmt.accept(this).forEach((t, u) -> {     //Les expressions evaluées dans la condition sont
            wl.gen.add(t);
        });

        wlList.put(e.toString(), wl);
        return null;
    }

    @Override
    public Map<String, String> visit(Eq e) {
        Map m = new HashMap();

        m.putAll(e.e1.accept(this));
        m.putAll(e.e2.accept(this));

        return m;
    }

    @Override
    public Map<String, String> visit(LE e) {
        Map m = new HashMap();

        m.putAll(e.e1.accept(this));
        m.putAll(e.e2.accept(this));

        return m;
    }

    @Override
    public Map<String, String> visit(Int e) {
        return new HashMap<>();
    }

    @Override
    public Map<String, String> visit(Label e) {
        return new HashMap<>();   //Les labels restent tels quels, pas de transformation à faire
    }

    @Override
    public Map<String, String> visit(Mem e) {
        Map m = new HashMap();
        WorkList wl = new WorkList(e);

        if (e.ident2 != null) {
            wl.kill.add(e.ident2.toString());

        }
        wl.gen.add(e.ident1.toString());

        return null;
    }

    @Override
    public Map<String, String> visit(Neg e) {
        return e.ident.accept(this);
    }

    @Override
    public Map<String, String> visit(New e) {

        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Map<String, String> visit(Nop e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Map<String, String> visit(ParenExp e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Map<String, String> visit(Tokens e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Map<String, String> visit(Fdiv e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Map<String, String> visit(Fmul e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Map<String, String> visit(Fneg e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Map<String, String> visit(Fsub e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Map<String, String> visit(Fadd e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
