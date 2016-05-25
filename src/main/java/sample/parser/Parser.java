package sample.parser;

import sample.exceptions.CouldNotParseException;
import sample.model.Recipe;

import java.io.*;
import java.util.ArrayList;

/* Class Parser
 *
 * Class Parser
 *
 * @author Markus
 */
public class Parser implements IParser
{
    public static Recipe parse(File recipeFile) throws  FileNotFoundException,CouldNotParseException
    {
        ArrayList<AConcreteParser> parsers = new ArrayList<>();
        // Parser instantiieren
        parsers.add(new TxtParser());
        //parsers.add(new CKParser ());
        parsers.add(new WWParser());

        // Format raten
        if(recipeFile.exists())
        {
            ArrayList<String> fileContent = new ArrayList<>();
            Recipe            recipe      = new Recipe();

            try {
                fileContent = readFile(recipeFile.toString());
            }
            catch(IOException e){
                // TODO: IOException behandeln
            }

            // Parser fragen
            for(int i=0;i<parsers.size();i++)
            {
                if(parsers.get(i).accepts(fileContent)){
                    try{
                        recipe = parsers.get(i).parse(fileContent);
                    }
                    catch(Exception e){
                        // TODO: Exception behandeln
                    }
                }
            }
            // Rezept prüfen
            // TODO: Rezept prüfen
            if(!recipe.isIncomplete())
                return recipe;
            else
                throw new CouldNotParseException();
        }
        else
        {
            throw new FileNotFoundException();
        }
    }

    static ArrayList<String> readFile(String file) throws IOException {
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

