package sample.parser;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.tuple.Triple;
import sample.exceptions.CouldNotParseException;
import sample.model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/* Class Parser
 *
 * Class Parser
 *
 * @author Markus
 */
public class Parser {


  /**
   * parse
   *
   * just to provide parsing file of given fileName
   *
   * @param fileName String name of the file that may contain the recipe content
   * @return IRecipe Recipe with the parsed content
   * @throws CouldNotParseException
   * @throws FileNotFoundException
   */
    public  static IRecipe parse(String fileName) throws  CouldNotParseException,FileNotFoundException {
        return parse(new File(fileName));
    }

    /**
     * parse
     *
     * main function of this parserController to be called in the UI.
     *
     * @param recipeFile File that contains the content of the recipe to parse
     * @return IRecipe the Recipe that is parsed from the given File.
     * @throws FileNotFoundException if the file does not exist
     * @throws CouldNotParseException if no parser present to parse the content of the existing file.
     */
    public static IRecipe parse(File recipeFile) throws CouldNotParseException, FileNotFoundException {
        // read file content
        List<String> fileContent;
        try { fileContent = FileUtils.readLines(recipeFile); }
        catch (IOException e) { throw new FileNotFoundException(e.getMessage()); }

        return Parser.parse(fileContent);
    }

    /**
     * parse
     *
     * just parse from List<string> linesOfFile
     *
     * @param fileContent
     * @return
     * @throws CouldNotParseException
     * @throws FileNotFoundException
     */
    public static IRecipe parse (List<String> fileContent) throws CouldNotParseException,FileNotFoundException {

        // add concrete parsers
        List<AConcreteParser> parsers = new ArrayList<>();
        parsers.add(new TxtParser());
        parsers.add(new CKParser());
        parsers.add(new WWParser());

        Recipe recipe = new Recipe();

        // call parsers
        for (AConcreteParser parser : parsers)
            if (parser.accepts(fileContent))
                recipe = parser.parse(fileContent);

        // check recipe
        if (!recipe.isIncomplete()) {
            // init null objects
            if(recipe.getNurture()  == null){ recipe.setNurture(new Nurture());   }
            if(recipe.getRegion()   == null){ recipe.setRegion(new Region());     }
            if(recipe.getCourse()   == null){ recipe.setCourse(new Course());     }
            if(recipe.getCategory() == null){ recipe.setCategory(new Category()); }
            if(recipe.getDaytime()  == null){ recipe.setDaytime(new Daytime());   }
            if(recipe.getSeason()   == null){ recipe.setSeason(new Season());     }
            if(recipe.getSource()   == null){ recipe.setSource(new Source());     }

            // fill null values
            if (recipe.getNurture() .getName() == null) recipe.getNurture() .setName("");
            if (recipe.getRegion()  .getName() == null) recipe.getRegion()  .setName("");
            if (recipe.getCourse()  .getName() == null) recipe.getCourse()  .setName("");
            if (recipe.getCategory().getName() == null) recipe.getCategory().setName("");
            if (recipe.getDaytime() .getName() == null) recipe.getDaytime() .setName("");
            if (recipe.getSeason()  .getName() == null) recipe.getSeason()  .setName("");
            if (recipe.getSource()  .getName() == null) recipe.getSource()  .setName("");

            List<Triple<IIngredient,Double,IUnit>> ingredients = recipe.getIngredients();
            for(Triple<IIngredient,Double,IUnit> ingredient:ingredients){
                if(ingredient.getRight().getName()==null) ingredient.getRight().setName("");
            }
            return recipe;
        }
        throw new CouldNotParseException();
    }
}

