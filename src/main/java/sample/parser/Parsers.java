package sample.parser;

/**
 * Created by sabine on 21.05.16.
 */
public class Parsers {
    private static Parsers ourInstance = new Parsers();

    public static Parsers getInstance() {
        return ourInstance;
    }

    private Parsers() {
    }
}
