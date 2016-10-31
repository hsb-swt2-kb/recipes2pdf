package sample;

import com.github.vbauer.herald.annotation.Log;
import com.google.common.collect.Lists;
import org.h2.tools.Server;
import org.slf4j.Logger;
import sample.config.IConfig;
import sample.database.DatabaseConfig;
import sample.database.dao.IGenericDAO;
import sample.exceptions.CouldNotParseException;
import sample.model.Cookbook;
import sample.ui.CLI;
import sample.ui.Shell;
import sample.ui.UI;

import javax.inject.Inject;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.DriverManager;
import java.util.List;


/**
 * Created by noex_ on 26.10.2016.
 */
public class Application {

    @Inject
    private UI ui;

    @Inject
    private IGenericDAO<Cookbook, Integer> cookbookDAO;

    @Log
    private Logger LOG;

    private DatabaseConfig databaseConfig = new DatabaseConfig( IConfig.getInstance() );

    public void run(final String[] args) {
        try {
            Server.createTcpServer("-tcpAllowOthers").start();
            Class.forName("org.h2.Driver");
            DriverManager.getConnection(  databaseConfig.getDatabaseURL() + ";AUTO_SERVER=TRUE;TRACE_LEVEL_FILE=4");
            final Server webServer = Server.createWebServer();
            webServer.start();
            LOG.debug("Server at: " + webServer.getURL() );
        } catch (Exception e) {
            LOG.error("cannot start H2 [{}]", e);
        }

        final Cookbook cookbook = new Cookbook();
        cookbook.setTitle("Neues Kochbuch");
        cookbookDAO.add(cookbook);

        try {
            final List<File> files = Lists.newArrayList(new File( "C:\\Users\\noex_\\IdeaProjects\\Java\\recipes2pdf\\src\\main\\resources\\sample\\Rezepte\\Bolognese.txt") );
            ui.addRecipes( files );
        } catch (CouldNotParseException | FileNotFoundException e) {
            e.printStackTrace();
        }

        //new GUI().start(args);
        /*final ICookbookDAO cookbookDAO = injector.getInstance(CookbookDAOImpl.class);
        final IRecipeDAO recipeDAO = injector.getInstance(RecipeDAOImpl.class);
        final ICategoryDAO categoryDAO = injector.getInstance(CategoryDAOImpl.class);*/
        /*
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

        cookbookDAO.addRecipe(cookbook, recipe);
        cookbookDAO.add(cookbook);
*/


        // select UI
        if(args.length==0)
            ;
        else if (args[0].equals("shell"))
            new Shell().start();
        else
            new CLI().start(args);
    }

}
