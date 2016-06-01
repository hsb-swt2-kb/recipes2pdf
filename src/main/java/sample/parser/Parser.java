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
    public static IRecipe parse(File recipeFile) throws CouldNotParseException, FileNotFoundException {
        List<AConcreteParser> parsers = new ArrayList<>();
        parsers.add(new TxtParser());
        parsers.add(new ChefkochParser());
        parsers.add(new WWParser());
        //parsers.add(new CKParser ());


        List<String> fileContent = null;
        try {
            fileContent = FileUtils.readLines(recipeFile);
        } catch (IOException e) {
            throw new FileNotFoundException(e.getMessage());
        }

        Recipe recipe = new Recipe();

        // Parser aufrufen
        for (AConcreteParser parser : parsers)
            if (parser.accepts(fileContent))
                recipe = parser.parse(fileContent);

        // Rezept prüfen
        if (!recipe.isIncomplete()) {
            //leere Werte behandeln

            if(recipe.getNurture() == null){
                INurture nurture = new Nurture();
                nurture.setName("");
                recipe.setNurture(nurture);
            }
            if(recipe.getRegion() == null){
                IRegion region = new Region();
                region.setName("");
                recipe.setRegion(region);
            }
            if(recipe.getCourse() == null){
                ICourse course = new Course();
                course.setName("");
                recipe.setCourse(course);
            }
            if(recipe.getCategory() == null){
                ICategory category = new Category();
                category.setName("");
                recipe.setCategory(category);
            }
            if(recipe.getDaytime() == null){
                IDaytime daytime = new Daytime();
                daytime.setName("");
                recipe.setDaytime(daytime);
            }
            if(recipe.getSeason() == null){
                ISeason season = new Season();
                season.setName("");
                recipe.setSeason(season);
            }
            if(recipe.getSource() == null){
                ISource source = new Source();
                source.setName("");
                recipe.setSource(source);
            }

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
        else
            throw new CouldNotParseException();
        // null -> "" bzw 0
    }

    /**
     * readFile
     * <p>
     * helpful function, because textparser can handle ArrayList<String>
     * easier than the content as one complete String.
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static ArrayList<String> readFile(String file) throws IOException {
        String line;
        ArrayList<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            return lines;
        }
    }
}

