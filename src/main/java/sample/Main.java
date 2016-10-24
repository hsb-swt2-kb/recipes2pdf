package sample;

import com.google.inject.Guice;
import com.google.inject.Injector;
import sample.database.Database;
import sample.database.DatabaseConnection;
import sample.database.DatabaseModule;
import sample.database.dao.*;
import sample.model.Category;
import sample.model.Cookbook;
import sample.model.Recipe;
import sample.ui.CLI;
import sample.ui.Shell;

/**
 * The Main starts the program.
 */
public class Main
{
    public static void main(String[] args) {
        new Database( DatabaseConnection.getDatabaseConnection() );
        Injector injector = Guice.createInjector(
            new DatabaseModule()
        );
        final ICookbookDAO cookbookDAO = injector.getInstance(CookbookDAOImpl.class);
        final IRecipeDAO recipeDAO = injector.getInstance(RecipeDAOImpl.class);
        final ICategoryDAO categoryDAO = injector.getInstance(CategoryDAOImpl.class);
        Cookbook cookbook = new Cookbook();
        cookbookDAO.add(cookbook);

        Category category = new Category();
        category.setName("Keine Ahnung");
        //category = categoryDAO.findOrCreate(category);
        category = categoryDAO.findOrCreate(category, "name", "Keine Ahnung");

        Recipe recipe = new Recipe();
        recipe.setTitle("Nudeln mit Soße");
        recipe = recipeDAO.findOrCreate(recipe, "title", "Nudeln mit Soße");
        recipeDAO.add(recipe, "Nudeln", 500, "g");
        recipeDAO.add(recipe, "Paprika", 2, "Stück");
        recipe.setCategory( category );
        recipeDAO.add(recipe);

        cookbook.addRecipe(recipe);
        cookbookDAO.add(cookbook);
        // select UI
        if(args.length==0)
            ;
        else if (args[0].equals("shell"))
            new Shell().start();
        else
            new CLI().start(args);
    }
}
