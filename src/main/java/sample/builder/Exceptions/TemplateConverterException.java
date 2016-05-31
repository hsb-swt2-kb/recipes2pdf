package sample.builder.Exceptions;

/**
 * Exception for JLR Converter. Is Thrown, when converting the Template to a specific .tex file fails
 */
public class TemplateConverterException extends Exception {
    public TemplateConverterException(String message){
        super(message);
    }
}
