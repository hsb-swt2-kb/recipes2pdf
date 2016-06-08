package sample.parser;

import sample.model.Recipe;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Henrik
 * last changed on 02.05.2016
 */
abstract class AConcreteParser implements IConcreteParser {

  protected Recipe recipe;
}
