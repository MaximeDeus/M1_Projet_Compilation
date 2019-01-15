package okalm.typechecking;

/**
 *
 * @author defoursr
 */
public class TypeException extends Exception{

    public TypeException(){
        super("Type checking error");
    }
    
    public TypeException(String msg){
        super(msg);
    }
}
