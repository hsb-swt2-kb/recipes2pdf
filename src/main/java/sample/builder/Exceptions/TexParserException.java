package sample.builder.Exceptions;

/**
 * Exception for JLR Parser. is thrown when a specific .tex file cannot be converted to a PDF file due to syntax
 * errors in .tex file.
 */
public class TexParserException extends Exception {
    public TexParserException(String message) {
        super(message);
    }
}
