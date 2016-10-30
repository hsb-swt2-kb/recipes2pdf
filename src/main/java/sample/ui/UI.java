package sample.ui;

import com.google.inject.Inject;
import javafx.collections.ObservableList;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.NotImplementedException;
import sample.builder.Builder;
import sample.builder.Exceptions.TexParserException;
import sample.builder.IBuilder;
import sample.builder.IConcreteBuilder;
import sample.builder.PdfBuilder;
import sample.config.IConfig;
import sample.database.dao.IGenericDAO;
import sample.database.service.RecipeService;
import sample.exceptions.CookBookNotFoundException;
import sample.exceptions.CouldNotParseException;
import sample.exceptions.RecipeNotFoundException;
import sample.model.Cookbook;
import sample.model.CookbookRecipe;
import sample.model.Recipe;
import sample.model.Sortlevel;
import sample.model.util.RecipeUtil;
import sample.parser.Parser;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Class UI
 * <p>
 * Used in GUI and CLI.
 * <p>
 * Created by markus
 */

public class UI {

    @Inject
    private IGenericDAO<Cookbook, Integer> cookbookDAO;
    @Inject
    private IGenericDAO<Recipe, Integer> recipeDAO;
    @javax.inject.Inject
    private RecipeService recipeService;

    void addRecipesFromFolder(final File folder) throws CouldNotParseException, FileNotFoundException {
        for (final File file : folder.listFiles()) {
            if (!file.isDirectory()) {
                this.addRecipe(file);
            }
            else {
                this.addRecipesFromFolder(file);
            }
        }
    }

    public void addRecipeFromHyperlink(final String URL) throws IOException,CouldNotParseException {
        List<String> lines = new ArrayList<>();
        String line;
        URL myUrl;
        BufferedReader in = null;
        try {
            myUrl = new URL(URL);
            in = new BufferedReader(new InputStreamReader(myUrl.openStream()));
            while ((line = in.readLine()) != null) {
                lines.add(line);
            }
        } finally {
            IOUtils.closeQuietly(in);
        }
        addRecipe(lines);
    }

    /**
     * addRecipes
     * <p>
     * multiple calls of addRecipe
     *
     * @param files List of the Files of the recipes to add to DB
     * @throws FileNotFoundException
     * @throws CouldNotParseException
     */
    public void addRecipes(List<File> files) throws CouldNotParseException, FileNotFoundException {
        for (File file : files) {
            addRecipe(file);
        }
    }

    /**
     * addRecipe
     * <p>
     * calls Parser to parse the Recipe out of the given File,
     * uses the IRecipeDAO to save the Recipe to the database.
     *
     * @param file File of the recipe to add to DB
     * @throws FileNotFoundException
     * @throws CouldNotParseException
     */
    void addRecipe(File file) throws CouldNotParseException, FileNotFoundException {
        Recipe recipe = Parser.parse(file);
        addToStandardCookBook(recipe);
    }

    /**
     * addRecipe
     * <p>
     * calls Parser to parse the Recipe out of the given File,
     * uses the IRecipeDAO to save the Recipe to the database.
     *
     * @param lines Lines of recipe to parse.
     * @throws FileNotFoundException
     * @throws CouldNotParseException
     */
    void addRecipe(List<String> lines) throws CouldNotParseException, FileNotFoundException {
        Recipe recipe = Parser.parse(lines);
        addToStandardCookBook(recipe);
    }

    /**
     * Adds recipe to the standard cookbook.
     *
     * @param recipe The recipe to add.
     */
    private void addToStandardCookBook(Recipe recipe) {
        if (null == recipe) {
            throw new IllegalStateException("Recipe is null.");
        } else if( RecipeUtil.isRecipeIncomplete(recipe) ) {
            throw new IllegalStateException("Recipe is incomplete.");
        } else {
            this.recipeService.create(recipe);
            Optional<Cookbook> oCookBook = this.cookbookDAO.findFirst("title", "Standardkochbuch");
            if(oCookBook.isPresent()) {
                final Cookbook cookbook = oCookBook.get();

                final CookbookRecipe cookbookRecipe = new CookbookRecipe();
                cookbookRecipe.setCookbook(cookbook);
                cookbookRecipe.setRecipe(recipe);

                cookbook.getCookbookRecipes().add(cookbookRecipe);

                this.cookbookDAO.update(cookbook);
            }
        }
    }

    /**
     * updateRecipe
     * <p>
     * uses the IRecipeDAO to update the data of the Recipe in the Database
     *
     * @param recipe recipe to update
     */
    void updateRecipe(Recipe recipe) {
        this.recipeDAO.update(recipe);
    }

    /**
     * getAllRecipesFromDB
     * <p>
     * uses the IRecipeDAO to get all Recipes out of the DB.
     *
     * @return List<Recipe> List of all Recipes present in DB
     */
    List<Recipe> getAllRecipesFromDB() {
        return this.recipeDAO.getAll();
    }

    /**
     * getAllCookbooksFromDB
     * <p>
     * uses the CookBookDAO to get all CookBooks out of the DB.
     *
     * @return List<Recipe> List of all Cookbooks present in DB
     */
    List<Cookbook> getAllCookbooksFromDB() {
        return this.cookbookDAO.getAll();
    }

    /**
     * delRecipes
     * <p>
     * uses IRecipeDAO to delete the given Recipe(s) from the DB
     *
     * @param recipes recipes to delete in DB
     */
    void delRecipesFromDatabase(ArrayList<Recipe> recipes) {
        for (Recipe recipe : recipes) {
            this.recipeDAO.remove(recipe);
        }
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
    void addCookBook(String title, ObservableList<String> sortLevelsOfTheCookbook, String foreWord, File picture) {
        Cookbook cookbook = new Cookbook();
        cookbook.setTitle(title);
        List<Sortlevel> sortlevelList = new ArrayList<>();
        sortLevelsOfTheCookbook.stream().map(s -> {
            Sortlevel sortlevel = new Sortlevel(); sortlevel.setName(s); return sortlevel;
        }).forEach(sortlevelList::add);
        cookbook.setSortlevel( sortlevelList );
        // TODO: Add foreword getForeWord()
        // TODO: Add picture getFile(()
        this.cookbookDAO.add(cookbook);
    }

    /**
     * createCookBook
     * <p>
     * inserts a cookbook to the database and returns the title if success, otherwise null
     *
     * @param cookbook cookbook to add to DB
     */
    void addCookBook(Cookbook cookbook) {
        this.cookbookDAO.add(cookbook);
    }

    /**
     * delCookBook
     * <p>
     * uses CookBookDAO to delete a Cookbook (only, not the Recipes of it) from the DB
     *
     * @param cookbookName name of the cookbook to delete from DB
     */
    void delCookBook(String cookbookName) {
        if(this.cookbookDAO.findFirst("name", cookbookName).isPresent())
        {
            Optional<Cookbook> cookbookToDelete = this.cookbookDAO.findFirst("name",cookbookName);
            if(cookbookToDelete.isPresent()) {
                this.cookbookDAO.remove(cookbookToDelete.get());
            } else {
                throw new IllegalStateException("Could not find cookbook to delete.");
            }
        }
    }

    /**
     * delCookBook
     * <p>
     * uses CookBookDAO to delete a Cookbook (only, not the Recipes of it) from the DB
     *
     * @param cookbook cookbook to delete from DB
     */
    void delCookBook(Cookbook cookbook) {
        this.cookbookDAO.remove(cookbook);
    }

    /**
     * changeCookBook
     * <p>
     * function to change the data of a Cookbook in the Database.
     *
     * @param cookbook cookbook which data have to be changed
     */
    void changeCookBook(Cookbook cookbook) {
        this.cookbookDAO.update(cookbook);
    }

    /**
     * changeRecipe
     * <p>
     * function to change the data of a Recipe in the DB.
     *
     * @param recipe the recipe which data has to be changed
     */
    void changeRecipe(Recipe recipe) {
        this.recipeDAO.update(recipe);
    }

    /**
     * delRecipe
     * <p>
     * call IRecipeDAO to remove the given Recipe from DB
     *
     * @param recipe the recipe that have to be deleted
     */
    void delRecipe(Recipe recipe) {
        this.recipeDAO.remove(recipe);
    }

    /**
     * searchCookBook
     * <p>
     * search for a Cookbook with given title calling ICookbookDAO.
     *
     * @param cookbookname the name of the cookbook to search
     * @return cookbook the cookbook that have been found in the database with the cookbookname
     * @throws CookBookNotFoundException
     */
    Cookbook searchCookBook(String cookbookname) throws CookBookNotFoundException {
        return this.cookbookDAO.findFirst("title", cookbookname).orElseThrow(CookBookNotFoundException::new);
    }

    /**
     * searchRecipe
     * <p>
     * search for a Recipe with given Title calling IRecipeDAO.
     *
     * @param recipeName the name of the recipe to search
     * @return recipe the recipe that have been found in the database with the recipeName
     * @throws RecipeNotFoundException
     */
    Recipe searchRecipe(String recipeName) throws RecipeNotFoundException {
        return this.recipeDAO.findFirst("title", recipeName).orElseThrow(RecipeNotFoundException::new);
    }

    File exportCookbook(String cookbookName, String paperFormats) throws CookBookNotFoundException,IOException,TexParserException{
        Cookbook cookbook = searchCookBook(cookbookName);
        List<IConcreteBuilder> builderList = new ArrayList<>();
        builderList.add(new PdfBuilder(IConfig.getInstance()));
        IBuilder builder = new Builder(builderList);
        return builder.build(cookbook);
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
    List<Recipe> getRecipesOfCookbook(String cookbookname) throws CookBookNotFoundException {
        Cookbook cookbook = this.searchCookBook(cookbookname);
        return cookbook.getRecipes();
    }

    public void addRecipeToCookbook(Cookbook cookbook, Recipe recipe) {
        //TODO: implement
        throw new NotImplementedException("Add recipe to cookbook is implemented yet");
    }

    public void removeRecipeFromCookbook(Cookbook cookbook, Recipe recipe) {
        //TODO: implement
        throw new NotImplementedException("Add recipe to cookbook is implemented yet");
    }
}
