package okalm.backend;

import okalm.asml.*;
import okalm.tools.AsmlObjVisitor;

import java.util.HashMap;
import java.util.Map;

/**
 *
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
        m.putAll(e.ident.accept(this));
        m.put(e.ident.toString(), "Kill");
        m.putAll(e.e.accept(this));
        m.putAll(e.asmt.accept(this));

        return m;
    }

    @Override
    public Map<String, String> visit(Call e) {
        Map m = new HashMap();
        e.fargs.forEach((farg) -> {
            m.putAll(farg.accept(this));
        });
        m.forEach((t, u) -> {
            u = "Gen";
        });

        return m;
    }

    @Override
    public Map<String, String> visit(CallClo e) {
        Map m = new HashMap();
        e.fargs.forEach((farg) -> {
            m.putAll(farg.accept(this));
        });
        m.forEach((t, u) -> {
            u = "Gen";
        });
        return m;
    }

    @Override
    public Map<String, String> visit(Fargs e) {
        Map m = new HashMap();
        m.put(e.ident.accept(this), "");
        return m;
    }

    @Override
    public Map<String, String> visit(Fundefs e) {
        Map m = new HashMap();
        String s = null;
        WorkList workl;
        WorkList wlTemp;

        //remplissage de la map des variables de ce bloc + les arguments formels de ce fonction
        m.putAll(e.asmt.accept(this));
        if (!e.formal_args.isEmpty()) {
            e.formal_args.forEach((element) -> {

                m.put(element.accept(this), "Gen");
            });
        }

        //Si un if dans ce bloc a deja crée une Worklist, one en sert, sinon on en fait une nouvelle
        if (m.containsKey("worklistmap")) {
            s = (String) m.get("worklistmap");
            wlTemp = wlList.get(s);
            wlTemp.exp = e;

        } else {
            wlTemp = new WorkList(e);

        }

        workl = wlTemp;

        //remplissage de la worklist avec les variables de ce bloc, sauvegardées dans la map m
        m.forEach((t, u) -> {
            if (u.equals("Gen")) {
                workl.gen.add((String) t);
            } else if (u.equals("Kill")) {
                workl.kill.add((String) t);
            }
        });

        wlList.put(workl.toString(), workl);

        if (!e.fundefs.isEmpty()) {
            e.fundefs.forEach((element) -> {
                element.accept(this);
            });
        }

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
        WorkList workl = new WorkList(e);
        WorkList wlthen, wlelse;
        Map m = new HashMap();
        Map mthen, melse;

        m.putAll(e.condasmt.accept(this)); //La condition fait partie du bloc actuel, alors on garde ses variables

        //On construit le worklist de la condition then
        mthen = new HashMap();
        mthen.putAll(e.thenasmt.accept(this));
        wlthen = new WorkList(e.condasmt);
        mthen.forEach((t, u) -> {
            if (u.equals("Gen")) {
                wlthen.gen.add((String) t);
            } else if (u.equals("Kill")) {
                wlthen.kill.add((String) t);
            }
        });

        if (mthen.containsKey("worklistmap")) {
            String s = (String) m.get("worklistmap");
            WorkList wlTemp = wlList.get(s);
            wlthen.suc.add(wlTemp.toString());
        }

        workl.suc.add(wlthen.toString());

        //On construit le worklist de la condition else
        melse = new HashMap();

        melse.putAll(e.elseasmt.accept(this));
        wlelse = new WorkList(e.elseasmt);
        melse.forEach((t, u) -> {
            if (u.equals("Gen")) {
                wlelse.gen.add((String) t);
            } else if (u.equals("Kill")) {
                wlelse.kill.add((String) t);
            }
        });

        if (melse.containsKey("worklistmap")) {
            String s = (String) m.get("worklistmap");
            WorkList wlTemp = wlList.get(s);
            wlelse.suc.add(wlTemp.toString());
        }

        workl.suc.add(wlelse.toString());

        m.put("worklistmap", workl.toString());
        wlList.put(workl.toString(), workl);
        return m;
    }

    @Override
    public Map<String, String> visit(Eq e) {
        Map m = new HashMap();

        m.putAll(e.e1.accept(this));
        m.putAll(e.e2.accept(this));
        m.forEach((t, u) -> {
            u = "Gen";
        });

        return m;
    }

    @Override
    public Map<String, String> visit(LE e) {
        Map m = new HashMap();

        m.putAll(e.e1.accept(this));
        m.putAll(e.e2.accept(this));
        m.forEach((t, u) -> {
            u = "Gen";
        });

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
        m.putAll(e.ident1.accept(this));
        m.putAll(e.identOrImm.accept(this));
        m.putAll(e.ident2.accept(this));
        return m;
    }

    @Override
    public Map<String, String> visit(Neg e) {
        return e.ident.accept(this);
    }

    @Override
    public Map<String, String> visit(New e) {

        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<String, String> visit(Nop e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<String, String> visit(ParenExp e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<String, String> visit(Tokens e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<String, String> visit(Fdiv e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<String, String> visit(Fmul e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<String, String> visit(Fneg e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<String, String> visit(Fsub e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<String, String> visit(Fadd e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
