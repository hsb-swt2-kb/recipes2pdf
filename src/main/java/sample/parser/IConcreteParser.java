package sample.parser;

import sample.model.Recipe;

import java.util.ArrayList;

/**
 * Created by markus on 21.05.16.
 */
public interface IConcreteParser {
    /**
     * parse
     *
     * method to call from the other components,
     * have to be implemented in the concrete parsers.
     *
     * @param text content of the recipe in the file
     * @return Recipe object that holds the structured recipe content
     * @throws Exception
     */
    public Recipe parse(ArrayList<String> text) throws Exception;

    /**
     * accepts
     *
     * method must be implemented in the concrete parsers
     * to be asked from other components if the concrete parser
     * can handle the given recipe-format.
     *
     * @param text
     * @return
     */
    public boolean accepts(ArrayList<String> text);
}
