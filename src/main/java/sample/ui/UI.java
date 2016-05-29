package sample.ui;

import sample.database.Database;
import sample.database.DatabaseConnection;
import sample.database.dao.CookbookDAO;
import sample.database.dao.RecipeDAO;
import sample.exceptions.CouldNotParseException;
import sample.model.Cookbook;
import sample.model.ICookbook;
import sample.model.IRecipe;
import sample.model.Recipe;
import sample.parser.Parser;

import java.io.File;
import java.io.FileNotFoundException;
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

    /**
     *
     *  addRecipes
     *
     *  multiple calls of addRecipe
     *
     * @param files List of the Files of the recipes to add to DB
     * @return boolean success of the insetions, false if one is not inserted
     * @throws FileNotFoundException
     * @throws CouldNotParseException
     */
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

    /**
     *
     *  addRecipe
     *
     *  calls Parser to parse the Recipe out of the given File,
     *  uses the RecipeDAO to save the Recipe to the database.
     *
     * @param file File of the recipe to add to DB
     * @return boolean success of the insertion
     * @throws FileNotFoundException
     * @throws CouldNotParseException
     */
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

    /**
     *
     *  updateRecipe
     *
     *  uses the RecipeDAO to update the data of the Recipe in the Database
     *
     * @param recipe recipe to update
     * @return boolean success of the update
     */
    static boolean updateRecipe(Recipe recipe){
        Database database = new Database(DatabaseConnection.getDatabaseConnection());
        return new RecipeDAO().update(recipe);
    }

    /**
     *
     *  getAllRecipesFromDB
     *
     *  uses the RecipeDAO to get all Recipes out of the DB.
     *
     * @return List<Recipe> List of all Recipes present in DB
     */
    static List<Recipe> getAllRecipesFromDB(){
        Database database = new Database(DatabaseConnection.getDatabaseConnection());
        return new RecipeDAO().getAll();
    }

    /**
     *
     *  getAllCookbooksFromDB
     *
     *  uses the CookBookDAO to get all CookBooks out of the DB.
     *
     * @return List<Recipe> List of all Cookbooks present in DB
     */
    static List<Cookbook> getAllCookbooksFromDB(){
        Database database = new Database(DatabaseConnection.getDatabaseConnection());
        return new CookbookDAO().getAll();
    }

    /**
     *
     *  castIRecipeToRecipe
     *
     *  converts an IRecipe to a Recipe.
     *
     * @param iRecipes iRecipes to cast to Recipe
     * @return List<Recipe> List of the converted Recipes
     */
    static public List<Recipe> castIRecipeToRecipe(List<IRecipe> iRecipes){
        List<Recipe> recipes = new ArrayList<>();
        for(IRecipe iRecipe : iRecipes){
            recipes.add((Recipe) iRecipe);
        }
        return recipes;
    }

    /**
     *
     *  castICookBookToCookBook
     *
     *  converts an ICookBook to a Cookbook.
     *
     * @param iCookbooks iCookbook to cast to Cookbook
     * @return List<Cookbook> List of the converted Cookbooks
     */
    static public List<Cookbook> castICookBookToCookBook(List<ICookbook> iCookbooks){
        List<Cookbook> cookbooks = new ArrayList<>();
        for(ICookbook iCookBook : iCookbooks){
            cookbooks.add((Cookbook) iCookBook);
        }
        return cookbooks;
    }

    /**
     *
     *  delRecipes
     *
     *  uses RecipeDAO to delete the given Recipe(s) from the DB
     *
     * @param recipes recipes to delete in DB
     * @return boolean successs of the deletion
     */
    static boolean delRecipes (ArrayList<Recipe> recipes){
        Database database = new Database(DatabaseConnection.getDatabaseConnection());
        boolean success = true;
        for(int i=0;i<recipes.size();i++)
            if(!new RecipeDAO().delete(recipes.get(i)))
                success=false;
        return success;
    }

    /**
     *
     * createCookBook
     *
     * inserts a cookbook to the database and returns the title if success, otherwise null
     *
     * @param cookbook cookbooks to add to DB
     * @return boolean success of the insertion
     */
    static boolean addCookBook(Cookbook cookbook){
        Database database = new Database(DatabaseConnection.getDatabaseConnection());
        return new CookbookDAO().insert(cookbook);
    }

    /**
     *
     *  delCookBook
     *
     *  uses CookBookDAO to delete a Cookbook (only, not the Recipes of it) from the DB
     *
     * @param cookbook cookbook to delete from DB
     * @return boolean success of the deletion
     */
    static boolean delCookBook(Cookbook cookbook){
        Database database = new Database(DatabaseConnection.getDatabaseConnection());
        return new CookbookDAO().delete(cookbook);

    }

    static boolean delRecipe(Recipe recipe) {
        Database database = new Database(DatabaseConnection.getDatabaseConnection());
        return new RecipeDAO().delete(recipe);

    }

    static Cookbook searchCookBook(String cookbookname) {
        Cookbook cookbookFromSearch = null;
        return cookbookFromSearch;

    }

    static Recipe searchRecipe(String recipe) {
        Recipe recipeFromSearch = null;
        return recipeFromSearch;

    }
}
