package sample.model;

import org.javalite.activejdbc.Base;
import sample.database.DatabaseConnection;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by czoeller on 25.03.16.
 */
public class TestORM {

    public static void main( String[] args ) throws SQLException {

        TestORM t = new TestORM();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Base.open(databaseConnection.CONNECTOR, databaseConnection.PATH, databaseConnection.USER, databaseConnection.PASSWORD);

        Recipe recipe = new Recipe();
        recipe
            .set("title", "First Recipe")
            .saveIt();
        recipe.getTitle();

        List<Recipe> recipeList = Recipe.findAll();
        recipeList.forEach(System.out::println);
        Recipe r1 = Recipe.findFirst("title = ?", "First Recipe");

        Cookbook c = new Cookbook();
        c.set("title", "A Cookbook").saveIt();
        Cookbook c1 = Cookbook.findFirst("title = ?", "A Cookbook");
        //c1.add( r1 );
        //c1.saveIt();

        final List<Recipe> allRecipesofC1 = c1.getAll(Recipe.class);
        allRecipesofC1.forEach(System.out::println);

        Ingredient ingredient1 = new Ingredient();
        ingredient1
            .set("name", "Zucker")
            .saveIt();

        RecipeIngredient recipeIngredient = RecipeIngredient.createIt("amount", 3);
        //recipeIngredient.add(ingredient1);
        //use one to many notation here:
        recipe.add(recipeIngredient);
        ingredient1.add(recipeIngredient);
    }
}
