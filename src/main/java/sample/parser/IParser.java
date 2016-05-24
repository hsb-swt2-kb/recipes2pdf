package sample.parser;

import sample.model.Recipe;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by sabine on 21.05.16.
 */
public interface IParser
{
    public boolean parse(File recipeFile) throws FileNotFoundException;
}
