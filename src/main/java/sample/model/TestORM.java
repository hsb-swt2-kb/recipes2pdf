package sample.model;

import sample.database.Database;
import sample.database.DatabaseConnection;
import sample.model.activejdbc.RecipeRepository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * Created by czoeller on 25.03.16.
 */
public class TestORM {

    public static void main(String[] args) {
        try {
            new TestORM().test();
        } catch (Exception ex) {
            // catch all exceptions to print them before crash
            ex.printStackTrace();
        }

    }

    private void test() throws IOException, SQLException {
        Database database = new Database(DatabaseConnection.getDatabaseConnection());
        database.drop();
        database.install();
        Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "Database recreated");
        createIngredients();
        createUnits();
        createRecipes();
        createCookbooks();
    }

    private void createIngredients() {
        String[] commonIngredients = {"Mehl", "Wasser", "Zucker", "Hefe", "Nudeln", "Paprika"};
        Stream.of(commonIngredients).forEach(name -> {
            IIngredient ingredient = IIngredient.getInstance();
            ingredient.setName(name);
            ingredient.saveIt();
        });
    }

    private void createUnits() {
        String[] commonUnits = {"kg", "TL"};
        Stream.of(commonUnits).forEach(unitName -> {
            IUnit unit = IUnit.getInstance();
            unit.setName(unitName);
            unit.saveIt();
        });
    }

    private void createRecipes() {
        IRecipe recipe = IRecipe.getInstance();
        recipe.setTitle("Nudeln mit Soße");
        recipe.saveIt();
        recipe.add("Nudeln", 500, "g");
        recipe.add("Paprika", 2, "Stück");
    }

    private void createCookbooks() {
        IRecipeRepository recipeRepository = new RecipeRepository();

        Optional<IRecipe> nudeln = recipeRepository.findFirst("title = ?", "Nudeln mit Soße");
        nudeln.orElseThrow(IllegalStateException::new);

        ICookbook cookbook = ICookbook.getInstance();
        cookbook.setTitle("First Cookbook");
        cookbook.saveIt();
        cookbook.addRecipe(nudeln.get());
    }
}
