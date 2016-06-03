package sample.parser;

import sample.model.Recipe;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Henrik
 * last changed on 02.05.2016
 */
abstract class AConcreteParser implements Constants,IConcreteParser {

    protected Recipe recipe;
    protected HTMLParserLib lib = new HTMLParserLib();

  public ArrayList<String> readFilecontent(String absoluterDateiPfad) throws IOException
  {
    String  thisFilerow = null;
    ArrayList<String> fileContent = new ArrayList<String>();

    FileReader fr = new FileReader(absoluterDateiPfad);
    BufferedReader br = new BufferedReader(fr);
    while ((thisFilerow = br.readLine()) != null)
    {
      fileContent.add(thisFilerow);
    }
    fr.close();
    br.close();

    return fileContent;
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
    /**
     * Method to put the IngredientsList into the recipe.
     *
     * @param ingredientsList ArrayList<String[]> containing all the ingredients split into amount, unit and ingredient.
     */
    protected void setRecipeIngredientsList(ArrayList<String[]> ingredientsList){
        for (String[] ingredient : ingredientsList) {
            if(ingredient[2] != null) {
                if (ingredient[0].isEmpty()) {
                    ingredient[0] = "0";
                }

                if (ingredient[1] == null) {
                    ingredient[1] = "";
                }

                double amount;
                try {
                    amount = Double.parseDouble(ingredient[0]);
                    amount = lib.round(amount);
                    recipe.add(ingredient[2], amount, ingredient[1]);
                } catch (Exception e) {
                    // TODO: exception handling? maybe ignore?
                }
            }
        }
    }
}
