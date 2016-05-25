package sample.ui;

import sample.database.dao.CookbookDAO;
import sample.database.dao.RecipeDAO;
import sample.exceptions.CouldNotParseException;
import sample.model.Cookbook;
import sample.model.ICookbook;
import sample.model.Recipe;
import sample.parser.Parser;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class UI
 *
 * Class UI holds static methods that are used in GUI and CLI.
 *
 * Created by markus
 */
public class UI {


    // multiple calls of addRecipe
    static List<Recipe> addRecipes (List<File> files) throws FileNotFoundException,CouldNotParseException {
        ArrayList<Recipe> recipeList = new ArrayList<>();
        for(int i=0;i<files.size();i++) {
            recipeList.add(Parser.parse(files.get(i)));
        }
        return recipeList;
    }

    // Calls Parser to parse the Recipe out of the given File
    static boolean addRecipe (File file) throws FileNotFoundException,CouldNotParseException {
        boolean success=true;
        Recipe recipe = new Recipe();
        recipe = Parser.parse(file);
        if (recipe.isIncomplete())
            success = false;
        else
        if(!new RecipeDAO().insert(recipe))
            success=false;
        return success;
    }

    static List<Recipe> getAllRecipesFromDB(){
        return new RecipeDAO().getAll();
    }

    static boolean delRecipes (ArrayList<Recipe> recipes){
        boolean success = true;
        for(int i=0;i<recipes.size();i++)
            if(!new RecipeDAO().delete(recipes.get(i)))
                success=false;
        return success;
    }

    /*
     * createCookBook
     *
     * inserts a cookbook to the database and returns the title if success, otherwise null
     */
    static boolean createCookBook(String cookBookName,String pictureFileName,String preamble){
        Cookbook cookbook = new Cookbook();
        cookbook.setTitle(cookBookName);
        //cookbook.setPicture(pictureFileName);
        //cookbook.setPreamble(preamble);

        return new CookbookDAO().insert(cookbook);
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
