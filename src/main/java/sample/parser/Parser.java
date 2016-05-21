package sample.parser;

import sample.model.Recipe;

import java.io.*;
import java.util.ArrayList;

/*
 *
 * @author Henrik
 */
public class Parser implements IParser
{
    private ArrayList<AConcreteParser> parsers;

    public Parser()
    {
        // Parser instantiieren
        parsers.add(new TxtParser());
        //parsers.add(new CKParser ());
        parsers.add(new WWParser ());
    }

    public Parser(ArrayList<AConcreteParser> parsers)
    {
        this.parsers=parsers;
    }

    private ArrayList<String> readFile(String file) throws IOException {
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

    public Recipe parse(File recipeFile) throws  FileNotFoundException
    {
        // Format raten
        if(recipeFile.exists())
        {
            ArrayList<String> fileContent = new ArrayList<String>();
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
            return recipe;
        }
        else
        {
            throw new FileNotFoundException();
        }
    }
}

