package sample.ui;

import sample.database.Database;
import sample.database.DatabaseConnection;
import sample.database.dao.CookbookDAO;
import sample.database.dao.RecipeDAO;
import sample.exceptions.CouldNotParseException;
import sample.model.Cookbook;
import sample.model.IRecipe;
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
    static boolean addRecipes (List<File> files) throws FileNotFoundException,CouldNotParseException {
        Database database = new Database(DatabaseConnection.getDatabaseConnection());
        ArrayList<Recipe> recipeList = new ArrayList<>();
        boolean success=true;
        for(int i=0;i<files.size();i++) {
            if(!addRecipe(files.get(i)))
                success=false;
        }
        return success;
    }

    // Calls Parser to parse the Recipe out of the given File
    static boolean addRecipe (File file) throws FileNotFoundException,CouldNotParseException {
        Database database = new Database(DatabaseConnection.getDatabaseConnection());
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
        Database database = new Database(DatabaseConnection.getDatabaseConnection());
        return new RecipeDAO().getAll();
    }
    static List<Cookbook> getAllCookbooksFromDB(){
        Database database = new Database(DatabaseConnection.getDatabaseConnection());
        return new CookbookDAO().getAll();
    }

    static public List<Recipe> castIRecipeToRecipe(List<IRecipe> iRecipes){
        List<Recipe> recipes = new ArrayList<>();
        for(IRecipe iRecipe : iRecipes){
            recipes.add((Recipe) iRecipe);
        }
        return recipes;
    }

    static boolean delRecipes (ArrayList<Recipe> recipes){
        Database database = new Database(DatabaseConnection.getDatabaseConnection());
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
    static boolean addCookBook(Cookbook cookbook){
        Database database = new Database(DatabaseConnection.getDatabaseConnection());
        return new CookbookDAO().insert(cookbook);
    }
}
