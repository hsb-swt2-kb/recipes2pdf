package sample;

import com.google.inject.Guice;
import com.google.inject.Injector;
import sample.database.Database;
import sample.database.DatabaseConnection;
import sample.database.DatabaseModule;
import sample.database.dao.CookbookDAOImpl;
import sample.database.dao.ICookbookDAO;
import sample.database.dao.IRecipeDAO;
import sample.database.dao.RecipeDAOImpl;
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
        Injector injector = Guice.createInjector(new DatabaseModule() );
        final ICookbookDAO cookbookDAO = injector.getInstance(CookbookDAOImpl.class);
        final IRecipeDAO recipeDAO = injector.getInstance(RecipeDAOImpl.class);
        cookbookDAO.add(new Cookbook());

        final Category category = new Category();
        category.setName("Keine Hnung ey");
        Recipe recipe = new Recipe();
        recipe.setTitle("Nudeln mit Soße");
        recipeDAO.add(recipe, "Nudeln", 500, "g");
        recipeDAO.add(recipe, "Paprika", 2, "Stück");
        recipe.setCategory( category );
        recipeDAO.add(recipe);

        // select UI
        if(args.length==0)
            ;
        else if (args[0].equals("shell"))
            new Shell().start();
        else
            new CLI().start(args);
    }
}
