package sample.model;

import sample.database.Database;
import sample.database.DatabaseConnection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by czoeller on 25.03.16.
 */
public class TestORM {

    public static void main(String[] args) throws SQLException {

        TestORM t = new TestORM();
        Database database = new Database( DatabaseConnection.getDatabaseConnection() );

        IRecipe recipe = IRecipe.getInstance();
        recipe.setTitle("First Recipe");
        recipe.saveIt();

        List<IRecipe> recipeList = new ArrayList<>();
        recipeList.addAll( Recipe.findAll() );
        recipeList.forEach(System.out::println);
        Recipe r1 = Recipe.findFirst("title = ?", "First Recipe");

        Cookbook c = new Cookbook();
        c.set("title", "A Cookbook").saveIt();
        Cookbook c1 = Cookbook.findFirst("title = ?", "A Cookbook");
        //c1.add( r1 );
        //c1.saveIt();

        final List<Recipe> allRecipesofC1 = c1.getAll(Recipe.class);
        allRecipesofC1.forEach(System.out::println);

        IIngredient ingredient1 = IIngredient.getInstance();
        ingredient1.setName("Zucker");
        System.out.println( ingredient1.toString() );
        ingredient1.saveIt();

        IRecipeIngredient recipeIngredient = IRecipeIngredient.getInstance();
        recipeIngredient = recipeIngredient.ccreateIt("amount", 3);
        System.out.println( recipeIngredient.toString() );

        ingredient1.add(recipeIngredient);
        //use one to many notation here:
        recipe.add(recipeIngredient);
    }
}
