package sample.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sample.database.Database;
import sample.database.DatabaseConnection;
import sample.model.activejdbc.RecipeRepository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by czoeller on 25.03.16.
 */
public class TestORM {

    private Logger LOG = LoggerFactory.getLogger(TestORM.class);

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
        LOG.warn("Database recreated");
        createCategories();
        createDaytimes();
        createNurtures();
        createSeasons();
        createIngredients();
        createUnits();
        createRecipes();
        createCookbooks();
        createSortlevels();
    }


    private void createCategories() {

    }

    private void createDaytimes() {
        String[] defaultDaytimes = {"Frühstück", "Mittag", "Abend"};
        Stream.of(defaultDaytimes).forEach(name -> {
            IDaytime daytime = IDaytime.getInstance();
            daytime.setName(name);
            daytime.saveIt();
        });
    }

    private void createNurtures() {
        String[] defaultNurtures = {"Low Carb", "vegetarisch", "Vegan", "Winter"};
        Stream.of(defaultNurtures).forEach(name -> {
            INurture nurture = INurture.getInstance();
            nurture.setName(name);
            nurture.saveIt();
        });
    }

    private void createSeasons() {
        String[] commonSeasons = {"Frühling", "Sommer", "Herbst", "Winter"};
        Stream.of(commonSeasons).forEach(name -> {
            ISeason season = ISeason.getInstance();
            season.setName(name);
            season.saveIt();
        });
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

    private void createSortlevels() {
        String[] defaultSortlevels = {"Kategorie", "Gerichtart", "Region", "Tageszeit", "Saison", "Ernaehrungsart", "Quelle"};
        Stream.of(defaultSortlevels).forEach(unitName -> {
            ISortlevel sortlevel = ISortlevel.getInstance();
            sortlevel.setName(unitName);
            sortlevel.saveIt();
        });
    }

}
