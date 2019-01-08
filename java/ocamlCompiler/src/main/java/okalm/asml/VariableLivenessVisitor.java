package okalm.asml;

import java.util.ArrayList;
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

    }

    @Override
    public Map<String, String> visit(Add e) {
        Map m = new HashMap();

        m.putAll(e.accept(this));
        m.putAll(e.ioi.accept(this));
        m.forEach((t, u) -> {
            u = "Gen";
        });

        return m;
    }

    @Override
    public Map<String, String> visit(Sub e) {

        Map m = new HashMap();

        m.putAll(e.accept(this));
        m.putAll(e.ioi.accept(this));
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
        WorkList workl = new WorkList(e);

        m.putAll(e.asmt.accept(this));

        if (!e.formal_args.isEmpty()) {
            e.formal_args.forEach((element) -> {

                m.put(element.accept(this), "Gen");
            });
        }

        if (m.containsKey("worklistmap")) {
            s = (String) m.get("worklistmap");
            WorkList wlTemp = wlList.get(s);
            workl.suc.add(wlTemp);
        }
/////////////////////////////////TODO
        if (!e.fundefs.isEmpty()) {
            e.fundefs.forEach((element) -> {

            });
        }

        return e;
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
            wlthen.suc.add(wlTemp);
        }

        workl.suc.add(wlthen);

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
            wlelse.suc.add(wlTemp);
        }

        workl.suc.add(wlelse);

        m.put("worklistmap", workl.toString());
        wlList.put(workl.toString(), workl);
        return m;
    }

    @Override
    public ArrayList<Exp_asml> visit(Int e) {
        ArrayList<Exp_asml> eas = new ArrayList<>();
        eas.add(e);
        return eas;
    }

    @Override
    public Exp_asml visit(Label e) {
        return e;   //Les labels restent tels quels, pas de transformation à faire
    }

    @Override
    public Exp_asml visit(Mem e) {
        e.ident1 = e.ident1.accept(this);
        e.ioi = e.ioi.accept(this);
        e.ident2 = e.ident2.accept(this);
        return e;
    }

    @Override
    public Exp_asml visit(Neg e) {
        e.ident = e.ident.accept(this);
        return e;
    }

    @Override
    public Exp_asml visit(New e) {
        e.ioi = e.ioi.accept(this);
        return e;
    }

    @Override
    public Exp_asml visit(Nop e) {
        return e;
    }

    @Override
    public Exp_asml visit(ParenExp e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Exp_asml visit(Tokens e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Exp_asml visit(Fdiv e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Exp_asml visit(Fmul e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Exp_asml visit(Fneg e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Exp_asml visit(Fsub e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Exp_asml visit(Fadd e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
