package sample.ui;

import javafx.collections.ObservableList;
import sample.builder.Builder;
import sample.builder.Exceptions.TexParserException;
import sample.builder.IBuilder;
import sample.builder.IConcreteBuilder;
import sample.builder.PdfBuilder;
import sample.config.IConfig;
import sample.database.Database;
import sample.database.DatabaseConnection;
import sample.database.dao.CookbookDAO;
import sample.database.dao.RecipeDAO;
import sample.exceptions.CookBookNotFoundException;
import sample.exceptions.CouldNotParseException;
import sample.exceptions.RecipeNotFoundException;
import sample.model.*;
import sample.parser.Parser;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Class UI
 * <p>
 * Class UI holds static methods that are used in GUI and CLI.
 * <p>
 * Created by markus
 */
public class UI {

    static void addRecipesFromFolder(final File folder) throws Exception {
        for (final File file : folder.listFiles()) {
            if (!file.isDirectory()) {
                try{
                    UI.addRecipe(file);
                }
                catch(Exception e){throw e;}
            }
            else {
                try{
                    UI.addRecipesFromFolder(file);
                }
                catch(Exception e){throw e;}
            }
        }
    }

    public static void addRecipeFromHyperlink(final String URL) throws IOException {
        String line = "";
        List<String> all = new ArrayList<>();
        URL myUrl;
        BufferedReader in = null;
        try {
            myUrl = new URL(URL);
            in = new BufferedReader(new InputStreamReader(myUrl.openStream()));

            while ((line = in.readLine()) != null) {
                all.add(line);
            }
        } finally {
            if (in != null) {
                in.close();
            }
        }
        addRecipe(all);
    }

    /**
     * addRecipes
     * <p>
     * multiple calls of addRecipe
     *
     * @param files List of the Files of the recipes to add to DB
     * @return boolean success of the insetions, false if one is not inserted
     * @throws FileNotFoundException
     * @throws CouldNotParseException
     */
    static void addRecipes(List<File> files) throws Exception {
        new Database(DatabaseConnection.getDatabaseConnection());
        for (File file : files) {
            addRecipe(file);
        }
    }

    /**
     * addRecipe
     * <p>
     * calls Parser to parse the Recipe out of the given File,
     * uses the RecipeDAO to save the Recipe to the database.
     *
     * @param file File of the recipe to add to DB
     * @return boolean success of the insertion
     * @throws FileNotFoundException
     * @throws CouldNotParseException
     */
    static void addRecipe(File file) throws Exception {
        new Database(DatabaseConnection.getDatabaseConnection());
        Recipe recipe = (Recipe) Parser.parse(file);
        if (!recipe.isIncomplete()) {
            new RecipeDAO().insert(recipe);
        }
        Cookbook cookbook = new CookbookDAO().findFirst("title=?", "Standardkochbuch").get();
        cookbook.addRecipe(recipe);
        new CookbookDAO().update(cookbook);
    }

    static void addRecipe(List<String> lines){

    }

    /**
     * updateRecipe
     * <p>
     * uses the RecipeDAO to update the data of the Recipe in the Database
     *
     * @param recipe recipe to update
     * @return boolean success of the update
     */
    static boolean updateRecipe(Recipe recipe) {
        new Database(DatabaseConnection.getDatabaseConnection());
        return new RecipeDAO().update(recipe);
    }

    /**
     * getAllRecipesFromDB
     * <p>
     * uses the RecipeDAO to get all Recipes out of the DB.
     *
     * @return List<Recipe> List of all Recipes present in DB
     */
    static List<Recipe> getAllRecipesFromDB() {
        new Database(DatabaseConnection.getDatabaseConnection());
        return new RecipeDAO().getAll();
    }

    /**
     * getAllCookbooksFromDB
     * <p>
     * uses the CookBookDAO to get all CookBooks out of the DB.
     *
     * @return List<Recipe> List of all Cookbooks present in DB
     */
    static List<Cookbook> getAllCookbooksFromDB() {
        new Database(DatabaseConnection.getDatabaseConnection());
        return new CookbookDAO().getAll();
    }

    /**
     * castIRecipeToRecipe
     * <p>
     * converts an IRecipe to a Recipe.
     *
     * @param iRecipes iRecipes to cast to Recipe
     * @return List<Recipe> List of the converted Recipes
     */
    static List<Recipe> castIRecipeToRecipe(List<IRecipe> iRecipes) {
        List<Recipe> recipes = new ArrayList<>();
        for (IRecipe iRecipe : iRecipes) {
            recipes.add((Recipe) iRecipe);
        }
        return recipes;
    }

    /**
     * castICookBookToCookBook
     * <p>
     * converts an ICookBook to a Cookbook.
     *
     * @param iCookbooks iCookbook to cast to Cookbook
     * @return List<Cookbook> List of the converted Cookbooks
     */
    static public List<Cookbook> castICookBookToCookBook(List<ICookbook> iCookbooks) {
        List<Cookbook> cookbooks = new ArrayList<>();
        for (ICookbook iCookBook : iCookbooks) {
            cookbooks.add((Cookbook) iCookBook);
        }
        return cookbooks;
    }

    /**
     * delRecipes
     * <p>
     * uses RecipeDAO to delete the given Recipe(s) from the DB
     *
     * @param recipes recipes to delete in DB
     * @return boolean successs of the deletion
     */
    static boolean delRecipesFromDatabase(ArrayList<Recipe> recipes) {
        new Database(DatabaseConnection.getDatabaseConnection());
        boolean success = true;
        for (Recipe recipe : recipes)
            if (!new RecipeDAO().delete(recipe))
                success = false;
        return success;
    }

    /**
     * createCookBook
     * <p>
     * inserts a cookbook to the database and returns the title if success, otherwise null
     *
     *
     * @param sortLevelsOfTheCookbook
     *@param foreWord of the cookbook
     * @param title cookbooks to add to DB  @return boolean success of the insertion
     */
    static void addCookBook(String title, ObservableList<String> sortLevelsOfTheCookbook, String foreWord, File picture) {
        new Database(DatabaseConnection.getDatabaseConnection());
        Cookbook cookbook = new Cookbook();
        cookbook.setTitle(title);
        List<ISortlevel> sortlevelList = new ArrayList<>();
        sortLevelsOfTheCookbook.stream().map(s -> {
            Sortlevel sortlevel = new Sortlevel(); sortlevel.setName(s); return sortlevel;
        }).forEach(sortlevelList::add);
        cookbook.setSortlevel( sortlevelList );
        // TODO: Add foreword getForeWord()
        // TODO: Add picture getFile(()
        new CookbookDAO().insert(cookbook);
    }

    /**
     * createCookBook
     * <p>
     * inserts a cookbook to the database and returns the title if success, otherwise null
     *
     * @param cookbook cookbook to add to DB
     * @return boolean success of the insertion
     */
    static void addCookBook(Cookbook cookbook) {
        new Database(DatabaseConnection.getDatabaseConnection());
        new CookbookDAO().insert(cookbook);
    }

    /**
     * delCookBook
     * <p>
     * uses CookBookDAO to delete a Cookbook (only, not the Recipes of it) from the DB
     *
     * @param cookbookName name of the cookbook to delete from DB
     * @return boolean success of the deletion
     */
    static boolean delCookBook(String cookbookName) {
        new Database(DatabaseConnection.getDatabaseConnection());
        if(new CookbookDAO().findFirst("name=?", cookbookName).isPresent())
        {
            Cookbook cookbookToDelete = new CookbookDAO().findFirst("name=?",cookbookName).get();
            return new CookbookDAO().delete(cookbookToDelete);
        }
        return false;
    }

    /**
     * delCookBook
     * <p>
     * uses CookBookDAO to delete a Cookbook (only, not the Recipes of it) from the DB
     *
     * @param cookbook cookbook to delete from DB
     * @return boolean success of the deletion
     */
    static boolean delCookBook(Cookbook cookbook) {
        new Database(DatabaseConnection.getDatabaseConnection());
        return new CookbookDAO().delete(cookbook);
    }

    /**
     * changeCookBook
     * <p>
     * function to change the data of a Cookbook in the Database.
     *
     * @param cookbook cookbook which data have to be changed
     */
    static boolean changeCookBook(Cookbook cookbook) {
        return new CookbookDAO().update(cookbook);
    }

    /**
     * changeRecipe
     * <p>
     * function to change the data of a Recipe in the DB.
     *
     * @param recipe the recipe which data has to be changed
     */
    static boolean changeRecipe(Recipe recipe) {
        return new RecipeDAO().update(recipe);
    }

    /**
     * delRecipe
     * <p>
     * call RecipeDAO to remove the given Recipe from DB
     *
     * @param recipe the recipe that have to be deleted
     * @return boolean success
     */
    static boolean delRecipe(Recipe recipe) {
        new Database(DatabaseConnection.getDatabaseConnection());
        return new RecipeDAO().delete(recipe);

    }

    /**
     * searchCookBook
     * <p>
     * search for a Cookbook with given title calling CookbookDAO.
     *
     * @param cookbookname the name of the cookbook to search
     * @return cookbook the cookbook that have been found in the database with the cookbookname
     * @throws CookBookNotFoundException
     */
    static Cookbook searchCookBook(String cookbookname) throws CookBookNotFoundException {
        Optional<Cookbook> cookbook = new CookbookDAO().findFirst("title=?",cookbookname);
        if(cookbook.isPresent())
            return cookbook.get();
        throw new CookBookNotFoundException();
        //return new CookbookDAO().findFirst("title=?", cookbookname).orElseThrow(CookBookNotFoundException::new);
    }

    /**
     * searchRecipe
     * <p>
     * search for a Recipe with given Title calling RecipeDAO.
     *
     * @param recipeName the name of the recipe to search
     * @return recipe the recipe that have been found in the database with the recipeName
     * @throws RecipeNotFoundException
     */
    static Recipe searchRecipe(String recipeName) throws RecipeNotFoundException {
        return new RecipeDAO().findFirst("title=?", recipeName).orElseThrow(RecipeNotFoundException::new);
    }

    static void exportCookbook(String cookbookName, String paperFormats) throws CookBookNotFoundException,IOException,TexParserException{
        try {
            ICookbook cookbook = searchCookBook(cookbookName);
            List<IConcreteBuilder> builderList = new ArrayList<>();
            builderList.add(new PdfBuilder(IConfig.getInstance()));
            IBuilder builder = new Builder(builderList);
            builder.build(cookbook);
        }
        catch (CookBookNotFoundException e) { throw e; }
        catch (IOException e) { throw e; }
        catch (TexParserException e) { throw e; }

    }

    /**
     * getRecipesOfCookbook
     *
     * to get the Recipes that are associated with this cookbook
     *
     * @param cookbookname name of the cookbook of you want the recipes from
     * @return List<Recipe> list of the recipes of the cookbook
     * @throws CookBookNotFoundException
     */
    static List<IRecipe> getRecipesOfCookbook(String cookbookname) throws CookBookNotFoundException {
        new Database(DatabaseConnection.getDatabaseConnection());
        Cookbook cookbook = UI.searchCookBook(cookbookname);
        return cookbook.getRecipes();
    }
}
