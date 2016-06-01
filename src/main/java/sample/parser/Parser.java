package sample.parser;

import org.apache.commons.io.FileUtils;
import sample.exceptions.CouldNotParseException;
import sample.model.IRecipe;
import sample.model.Recipe;

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
        if (!recipe.isIncomplete())
            return recipe;
        else
            throw new CouldNotParseException();
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

