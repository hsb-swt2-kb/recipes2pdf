package sample.parser;

import sample.model.Recipe;

import java.util.ArrayList;

/**
 * Created by sabine on 21.05.16.
 */
public interface IConcreteParser {
    public Recipe parse(ArrayList<String> text);
    public boolean accepts(ArrayList<String> text);
}
