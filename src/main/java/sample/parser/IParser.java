package sample.parser;

import sample.model.IRecipe;

/**
 * Interface to parse recipe text to recipe.
 * Created by czoeller on 31.03.16.
 */
public interface IParser {

    /**
     * Parse a recipe text to recipe.
     * @param text The recipe as text. E.g. plain text or html etc.
     * @return IRecipe The populated IRecipe
     * @throws Exception The parse should throw Exceptions appropriately.
     */
    IRecipe parse(String text) throws Exception;

    /**
     * Decide whether the parser is responsible for this kind of recipe or not.
     * @param text The recipe as text.
     * @return true if accepts
     */
    boolean accepts(String text);
}

