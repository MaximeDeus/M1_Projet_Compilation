/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asml;

/**
 *
 * @author liakopog
 */
public class Add extends Exp_asml {

    Ident ident;
    IdentOrImm ioi;

    @Override
    public <E> E accept(ObjVisitorAsml<E> v) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

	@Override
	public void accept(VisitorAsml v) {
		// TODO Auto-generated method stub
		
	}

}