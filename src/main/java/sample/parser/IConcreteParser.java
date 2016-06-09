package sample.parser;

import sample.exceptions.CouldNotParseException;
import sample.model.Recipe;
import java.util.List;

/**
 * implemented by markus on 21.05.16
 */
interface IConcreteParser {
    /**
     * parse
     *
     * method to call from the other components,
     * have to be implemented in the concrete parsers.
     *
     * @param text content of the recipe in the file
     * @return Recipe object that holds the structured recipe content
     * @throws CouldNotParseException
     */
    Recipe parse(List<String> text) throws CouldNotParseException;

    /**
     * accepts
     *
     * method must be implemented in the concrete parsers
     * to be asked from other components if the concrete parser
     * can handle the given recipe-format.
     *
     * @param text content of the recipe to parse
     * @return boolean if the concrete parser can handle the given content
     */
    boolean accepts(List<String> text);
}
