package sample.database.dbo;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import sample.database.Database;
import sample.database.DatabaseConnection;
import sample.database.dao.*;
import sample.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by czoeller on 30.03.16.
 */
public class ADatabaseTest {

    private static boolean setUpIsDone = false;

    @Before
    public void before() throws IOException, SQLException {
        Database database = new Database( DatabaseConnection.getSandboxDatabaseConnection() );
        if (!setUpIsDone) {
            database.drop();
            database.install();
            populateSampleData();
            setUpIsDone = true;
        }
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
        createCourses();
        createRegions();
    }

    private void createCategories() {
        final CategoryDAO categoryDAO = new CategoryDAO();
        Stream.of("Greek", "Italian", "German").forEach(name -> {
            Category category = new Category();
            category.setName(name);
            categoryDAO.insert(category);
        });
    }

    private void createDaytimes() {
        final DaytimeDAO daytimeDAO = new DaytimeDAO();
        Stream.of("Frühstück", "Mittag", "Abend").forEach(name -> {
            Daytime daytime = new Daytime();
            daytime.setName(name);
            daytimeDAO.insert(daytime);
        });
    }

    private void createNurtures() {
        final NurtureDAO nurtureDAO = new NurtureDAO();
        Stream.of("Low Carb", "vegetarisch", "Vegan").forEach(name -> {
            Nurture nurture = new Nurture();
            nurture.setName(name);
            nurtureDAO.insert(nurture);
        });
    }

    private void createSeasons() {
        final SeasonDAO seasonDAO = new SeasonDAO();
        Stream.of("Frühling", "Sommer", "Herbst", "Winter").forEach(name -> {
            Season season = new Season();
            season.setName(name);
            seasonDAO.insert(season);
        });
    }

    private void createIngredients() {
        final IngredientDAO ingredientDAO = new IngredientDAO();
        Stream.of("Mehl", "Wasser", "Zucker", "Hefe", "Nudeln", "Paprika").forEach(name -> {
            Ingredient ingredient = new Ingredient();
            ingredient.setName(name);
            ingredientDAO.insert(ingredient);
        });
    }

    private void createUnits() {
        final UnitDAO unitDAO = new UnitDAO();
        Stream.of("kg", "TL").forEach(unitName -> {
            Unit unit = new Unit();
            unit.setName(unitName);
            unitDAO.insert(unit);
        });
    }

    private void createRecipes() {
        final RecipeDAO recipeDAO = new RecipeDAO();
        final Category category = new Category();
        category.setName("Keine Hnung ey");
        Recipe recipe = new Recipe();
        recipe.setTitle("Nudeln mit Soße");
        recipe.add("Nudeln", 500, "g");
        recipe.add("Paprika", 2, "Stück");
        recipe.setCategory( category );
        recipeDAO.insert(recipe);
    }

    private void createCookbooks() {
        final RecipeDAO recipeDAO = new RecipeDAO();
        final CookbookDAO cookbookDAO = new CookbookDAO();
        Optional<Recipe> nudeln = recipeDAO.findFirst("title = ?", "Nudeln mit Soße");
        nudeln.orElseThrow(IllegalStateException::new);

        Cookbook cookbook = new Cookbook();
        cookbook.setTitle("First Cookbook");
        cookbook.addRecipe(nudeln.get());
        cookbookDAO.insert(cookbook);
    }

    private void createSortlevels() {
        final SortlevelDAO sortlevelDAO = new SortlevelDAO();
        Stream.of("Kategorie", "Gerichtart", "Region", "Tageszeit", "Saison", "Ernaehrungsart", "Quelle").forEach(name -> {
            Sortlevel sortlevel = new Sortlevel();
            sortlevel.setName(name);
            sortlevelDAO.insert(sortlevel);
        });
    }

    private void createCourses() {
        final CourseDAO courseDAO = new CourseDAO();
        Stream.of("first course", "second course", "main course").forEach(name -> {
            Course course = new Course();
            course.setName(name);
            courseDAO.insert(course);
        });
    }

    private void createRegions() {
        final RegionDAO regionDAO = new RegionDAO();
        Stream.of("Greek", "Italy").forEach(name -> {
            Region region = new Region();
            region.setName(name);
            regionDAO.insert(region);
        });
    }

    @After
    public void after() {
        Base.rollbackTransaction();
        Base.close();
    }
}
