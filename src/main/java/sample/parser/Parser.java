package sample.parser;

import sample.exceptions.CouldNotParseException;
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
public class Parser implements IParser
{
    /**
     * parse
     *
     * implementation of the parse method from IParser
     *
     * @param recipeFile
     * @return
     * @throws FileNotFoundException
     * @throws CouldNotParseException
     */
    public static Recipe parse(File recipeFile) throws  Exception,NullPointerException,IOException,FileNotFoundException,CouldNotParseException
    {
        List<AConcreteParser> parsers = new ArrayList<>();
        parsers.add(new TxtParser());
        parsers.add(new WWParser());
        //parsers.add(new CKParser ());

        if(recipeFile.exists())
        {
            ArrayList<String> fileContent = readFile(recipeFile.toString());
            Recipe            recipe      = new Recipe();

            // Parser aufrufen
            for(AConcreteParser parser:parsers)
                if(parser.accepts(fileContent))
                    recipe = parser.parse(fileContent);

            // Rezept prüfen
            if(!recipe.isIncomplete())
                return recipe;
            else
                throw new CouldNotParseException();
        }
        else
            throw new FileNotFoundException();
    }

    /**
     * readFile
     *
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

