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

    public Parser()
    {
    }

    static public Recipe parse(File recipeFile) throws FileNotFoundException,CouldNotParseException
    {
        ArrayList<AConcreteParser> parsers= new ArrayList<>();
        // Parser instantiieren
        parsers.add(new TxtParser());
        //parsers.add(new CKParser ());
        parsers.add(new WWParser ());

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

            // Defaultwert für leere Felder setzen
            if(recipe.getCategory().getName() == null)
                recipe.getCategory().setName("");
            if(recipe.getCourse().getName() == null)
                recipe.getCourse().setName("");
            if(recipe.getSeason().getName() == null)
                recipe.getSeason().setName("");
            if(recipe.getRegion().getName() == null)
                recipe.getRegion().setName("");
            if(recipe.getNurture().getName() == null)
                recipe.getNurture().setName("");

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
    static public ArrayList<String> readFile(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String         line = null;
        ArrayList<String> lines = new ArrayList<String>();

        try {
            while((line = reader.readLine()) != null) {
                lines.add(line);
            }

            return lines;
        } finally {
            reader.close();
        }
    }
}

