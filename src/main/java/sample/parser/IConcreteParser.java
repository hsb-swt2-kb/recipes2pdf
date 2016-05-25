package sample.parser;

import sample.model.Recipe;

import java.util.ArrayList;

/**
 * Created by markus on 21.05.16.
 */
public interface IConcreteParser {
    public Recipe parse(ArrayList<String> text) throws Exception;
    public boolean accepts(ArrayList<String> text);
}
