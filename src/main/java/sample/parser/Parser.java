package sample.parser;

import org.apache.commons.io.FileUtils;
import sample.exceptions.CouldNotParseException;
import sample.model.*;
import sample.model.util.RecipeUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* Class Parser
 *
 * Class Parser
 *
 * @author Markus
 */
public class Parser implements IParser {

    /**
     * parse
     * <p>
     * implementation of the parse method from IParser
     *
     * @param recipeFile
     * @return
     * @throws FileNotFoundException
     * @throws CouldNotParseException
     */
    public static Recipe parse(File recipeFile) throws CouldNotParseException, FileNotFoundException {
        // read file content
        List<String> fileContent;
        try { fileContent = FileUtils.readLines(recipeFile); }
        catch (IOException e) { throw new FileNotFoundException(e.getMessage()); }

        return Parser.parse(fileContent);
    }


    public static Recipe parse (List<String> fileContent) throws CouldNotParseException,FileNotFoundException {

        // add concrete parsers
        List<AConcreteParser> parsers = new ArrayList<>();
        parsers.add(new TxtParser());
        parsers.add(new ChefkochParser());
        parsers.add(new WWParser());


        Recipe recipe = new Recipe();

        // call parsers
        for (AConcreteParser parser : parsers)
            if (parser.accepts(fileContent))
                recipe = parser.parse(fileContent);

        // check recipe
        if (!RecipeUtil.isRecipeIncomplete(recipe)) {
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

            final Collection<RecipeIngredient> recipeIngredients = recipe.getRecipeIngredients();

            for( RecipeIngredient recipeIngredient : recipeIngredients){
                if( recipeIngredient.getUnit().getName()==null) recipeIngredient.getUnit().setName("");
            }

            return recipe;
        }
        throw new CouldNotParseException();
    }

    /**
     * readFile
     * <p>
     * helpful function, because textparser can handle ArrayList<String>
     * easier than the content as one complete String.
     *
     * @param file file to read out
     * @return
     * @throws IOException
     */
    protected static ArrayList<String> readFile(String file) throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        String line;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            return lines;
        }
    }
}

