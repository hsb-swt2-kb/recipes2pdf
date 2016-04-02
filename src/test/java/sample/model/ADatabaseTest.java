package sample.model;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import sample.database.Database;
import sample.database.DatabaseConnection;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by czoeller on 30.03.16.
 */
public class ADatabaseTest {

    @Before
    public void before() throws IOException, SQLException {
        Database database = new Database( DatabaseConnection.getSandboxDatabaseConnection() );
        database.drop();
        database.install();
        populateSampleData();
        Base.openTransaction();
    }

    private void populateSampleData() {
        IRecipe recipe = IRecipe.getInstance();
        recipe.setTitle("First Recipe");
        recipe.saveIt();

        List<Recipe> recipeList = Recipe.findAll();
        recipeList.forEach(System.out::println);
        Recipe r1 = Recipe.findFirst("title = ?", "First Recipe");

        Cookbook c = new Cookbook();
        c.set("title", "A Cookbook").saveIt();
        Cookbook c1 = Cookbook.findFirst("title = ?", "A Cookbook");
        c1.add( r1 );
        c1.saveIt();

        final List<Recipe> allRecipesofC1 = c1.getAll(Recipe.class);
        allRecipesofC1.forEach(System.out::println);

        Ingredient ingredient1 = new Ingredient();
        ingredient1
            .set("name", "Zucker")
            .saveIt();

        //RecipeIngredient recipeIngredient = RecipeIngredient.ccreateIt("amount", 3);
        //recipeIngredient.add(ingredient1);
        //use one to many notation here:
        //recipe.add(recipeIngredient);
        //ingredient1.add(recipeIngredient);
    }

    @After
    public void after() {
        Base.rollbackTransaction();
        Base.close();
    }
}