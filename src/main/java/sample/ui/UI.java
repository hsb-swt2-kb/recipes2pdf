package sample.ui;

import sample.database.Database;
import sample.database.DatabaseConnection;
import sample.database.dao.CookbookDAO;
import sample.database.dao.RecipeDAO;
import sample.exceptions.CookBookNotFoundException;
import sample.exceptions.CouldNotParseException;
import sample.exceptions.RecipeNotFoundException;
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
        boolean success=true;
        for(File file : files) {
            if(!addRecipe(file))
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
        Recipe recipe = Parser.parse(file);
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
    static List<Recipe> castIRecipeToRecipe(List<IRecipe> iRecipes){
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
        for(Recipe recipe : recipes)
            if(!new RecipeDAO().delete(recipe))
                success=false;
        return success;
    }

    /**
     *
     * createCookBook
     *
     * inserts a cookbook to the database and returns the title if success, otherwise null
     *
     * @param title cookbooks to add to DB
     * @return boolean success of the insertion
     */
    static boolean addCookBook(String title){
        Database database = new Database(DatabaseConnection.getDatabaseConnection());
        Cookbook cookbook = new Cookbook();
        cookbook.setTitle(title);
        return new CookbookDAO().insert(cookbook);
    }
    /**
     *
     * createCookBook
     *
     * inserts a cookbook to the database and returns the title if success, otherwise null
     *
     * @param cookbook cookbook to add to DB
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

    /**
     * changeCookBook
     *
     * function to change the data of a Cookbook in the Database.
     *
     * @param cookbook cookbook which data have to be changed
     */
    static boolean changeCookBook(Cookbook cookbook){
        return new CookbookDAO().update(cookbook);
    }

    /**
     * changeRecipe
     *
     * function to change the data of a Recipe in the DB.
     *
     * @param recipe the recipe which data has to be changed
     */
    static boolean changeRecipe(Recipe recipe){
        return new RecipeDAO().update(recipe);
    }

    /**
     * delRecipe
     *
     * call RecipeDAO to remove the given Recipe from DB
     *
     * @param recipe the recipe that have to be deleted
     * @return boolean success
     */
    static boolean delRecipe(Recipe recipe) {
        Database database = new Database(DatabaseConnection.getDatabaseConnection());
        return new RecipeDAO().delete(recipe);

    }

    /**
     * searchCookBook
     *
     * search for a Cookbook with given title calling CookbookDAO.
     *
     * @param cookbookname the name of the cookbook to search
     * @return cookbook the cookbook that have been found in the database with the cookbookname
     * @throws CookBookNotFoundException
     */
    static Cookbook searchCookBook(String cookbookname) throws CookBookNotFoundException {
        List<Cookbook> cookBooks = new ArrayList<>();
          cookBooks = new CookbookDAO().getAll();
        for(Cookbook cookbook:cookBooks)

            if(cookbook.getTitle().equals(cookbookname))
                return cookbook;
        throw new CookBookNotFoundException();

    }

    /**
     * searchRecipe
     *
     * search for a Recipe with given Title calling RecipeDAO
     *
     * @param recipeName the name of the recipe to search
     * @return recipe the recipe that have been found in the database with the recipeName
     * @throws RecipeNotFoundException
     */
    static Recipe searchRecipe(String recipeName) throws  RecipeNotFoundException{
        Recipe recipeFromSearch = null;
        List<Recipe> recipes = new ArrayList<>();
        recipes = new RecipeDAO().getAll();
        for(Recipe recipe : recipes)
            if(recipe.getTitle().equals(recipeName))
                return recipe;
        throw new RecipeNotFoundException();
    }
}
