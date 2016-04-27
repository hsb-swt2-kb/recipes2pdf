package sample.model.activejdbc;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import sample.database.Database;
import sample.database.DatabaseConnection;
import sample.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.stream.Stream;

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
        Stream.of("Frühstück", "Mittag", "Abend").forEach(name -> {
            IDaytime daytime = IDaytime.getInstance();
            daytime.setName(name);
            daytime.saveIt();
        });
    }

    private void createNurtures() {
        Stream.of("Low Carb", "vegetarisch", "Vegan", "Winter").forEach(name -> {
            INurture nurture = INurture.getInstance();
            nurture.setName(name);
            nurture.saveIt();
        });
    }

    private void createSeasons() {
        Stream.of("Frühling", "Sommer", "Herbst", "Winter").forEach(name -> {
            ISeason season = ISeason.getInstance();
            season.setName(name);
            season.saveIt();
        });
    }

    private void createIngredients() {
        Stream.of("Mehl", "Wasser", "Zucker", "Hefe", "Nudeln", "Paprika").forEach(name -> {
            IIngredient ingredient = IIngredient.getInstance();
            ingredient.setName(name);
            ingredient.saveIt();
        });
    }

    private void createUnits() {
        Stream.of("kg", "TL").forEach(unitName -> {
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
        Stream.of("Kategorie", "Gerichtart", "Region", "Tageszeit", "Saison", "Ernaehrungsart", "Quelle").forEach(unitName -> {
            ISortlevel sortlevel = ISortlevel.getInstance();
            sortlevel.setName(unitName);
            sortlevel.saveIt();
        });
    }

    @After
    public void after() {
        Base.rollbackTransaction();
        Base.close();
    }
}
