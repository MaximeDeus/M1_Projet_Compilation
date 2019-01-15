/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package okalm.typechecking;

/**
 *
 * @author defoursr
 */
public class TypeException extends Exception {

    public TypeException(){
        super("Type checking error");
    }

    public TypeException(String msg) {
        super(msg);
    }
}
