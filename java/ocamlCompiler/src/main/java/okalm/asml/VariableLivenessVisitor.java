package okalm.asml;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author liakopog
 */
public class VariableLivenessVisitor implements AsmlObjVisitor<List<String>> {

    @Override
    public List<String> visit(Add e) {
        ArrayList<String> temp = new ArrayList(e.ident.accept(this));
        temp.addAll(e.ioi.accept(this));
        return temp;
    }

    @Override
    public List<String> visit(Sub e) {
        ArrayList<String> temp = new ArrayList(e.ident.accept(this));
        temp.addAll(e.ioi.accept(this));
        return temp;
    }

    @Override
    public List<String> visit(Asmt e) {
        
        ArrayList<String> temp = new ArrayList(e.ident.accept(this));
        return temp;
    }


    

}
