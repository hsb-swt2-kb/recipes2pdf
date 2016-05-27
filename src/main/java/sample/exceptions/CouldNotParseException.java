package sample.exceptions;

/**
 * implemented by on 24.05.16 by markus
 */
public class CouldNotParseException extends  Exception{
    public CouldNotParseException(){}
    public CouldNotParseException(String message){
        super(message);
    }
}
