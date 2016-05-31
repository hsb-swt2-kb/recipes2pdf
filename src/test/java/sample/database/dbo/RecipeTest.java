package sample.database.dbo;

import org.apache.commons.lang3.tuple.Triple;
import org.junit.Before;
import org.junit.Test;
import sample.database.dao.*;
import sample.model.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.javalite.test.jspec.JSpec.the;

/**
 * Created by czoeller on 08.04.16.
 * These tests are actually using the database.
 * Each test is executed in an own transaction that is rolled back after the test.
 * @see ADatabaseTest
 * Precondition: These tests require the sandbox database that contains sample data for tests.
 */
public class RecipeTest extends ADatabaseTest {

    Recipe recipe;
    RecipeDAO recipeDAO;

    @Before
    public void setUp() {
        this.recipeDAO = new RecipeDAO();
        final Recipe recipe = new Recipe();
        final Optional<Recipe> nudeln = recipeDAO.findFirst("title = ?", "Nudeln mit Soße");
        this.recipe = nudeln.orElseThrow(IllegalStateException::new);
    }

    /**
     * Postcondition: The title of the recipe is changed.
     */
    @Test
    public void testTitle() {
        String title = "The title of the recipe.";
        recipe.setTitle(title);
        recipeDAO.update(recipe);
        final Recipe byId = recipeDAO.findById(recipe.getID()).get();
        the(byId.getTitle()).shouldBeEqual(title);
    }

    /**
     * Postcondition: The text of the recipe is changed.
     */
    @Test
    public void testText() {
        String text = "Description of the recipe. The steps etc.";
        recipe.setText(text);
        recipeDAO.update(recipe);
        final Recipe byId = recipeDAO.findById(recipe.getID()).get();
        the(byId.getText()).shouldBeEqual(text);
    }

    /**
     * Postcondition: The portions of the recipe are changed.
     */
    @Test
    public void testPortions() {
        int portions = 3;
        recipe.setPortions(3);
        recipeDAO.update(recipe);
        final Recipe byId = recipeDAO.findById(recipe.getID()).get();
        the(byId.getPortions()).shouldBeEqual(portions);
    }

    /**
     * Postcondition: The duration of the recipe is changed.
     */
    @Test
    public void testDuration() {
        int duration = 3;
        recipe.setDuration(duration);
        recipeDAO.update(recipe);
        final Recipe byId = recipeDAO.findById(recipe.getID()).get();
        the(byId.getDuration()).shouldBeEqual(duration);
    }

    /**
     * Postcondition: The calories of the recipe are changed.
     */
    @Test
    public void testCalories() {
        int setCalories = 300;
        recipe.setCalories(setCalories);
        recipeDAO.update(recipe);
        final Recipe byId = recipeDAO.findById(recipe.getID()).get();
        the(byId.getCalories()).shouldBeEqual(setCalories);
    }

    /**
     * Postcondition: The category of the recipe is changed.
     */
    @Test
    public void testCategory() {
        Category category = new Category();
        String categoryName = "Example CategoryDBO";
        category.setName(categoryName);
        recipe.setCategory(category);
        recipeDAO.update(recipe);
        final Recipe byId = recipeDAO.findById(recipe.getID()).get();
        the(byId.getCategory().getName()).shouldBeEqual(categoryName);
    }

    /**
     * Postcondition: The course of the recipe is changed.
     */
   @Test
    public void testCourse() {
        CourseDAO courseDAO = new CourseDAO();
        Course course = new Course();
        String courseName = "dessert";
        course.setName(courseName);
        courseDAO.insert(course);
        recipe.setCourse(course);
        recipeDAO.update(recipe);
        final Recipe byId = recipeDAO.findById(recipe.getID()).get();
        the(byId.getCourse().getName()).shouldBeEqual(courseName);
    }

    /**
     * Postcondition: The region of the recipe is changed.
     */
    @Test
    public void testRegion() {
        Region region = new Region();
        String regionName = "Spain";
        region.setName(regionName);
        recipe.setRegion(region);
        recipeDAO.update(recipe);
        final Recipe byId = recipeDAO.findById(recipe.getID()).get();
        the(byId.getRegion().getName()).shouldBeEqual(regionName);
    }

    /**
     * Postcondition: The daytime of the recipe is changed.
     */
    @Test
    public void testDaytime() {
        final DaytimeDAO daytimeDAO = new DaytimeDAO();
        Daytime daytime = new Daytime();
        String daytimeName = "Breakfast";
        daytime.setName(daytimeName);
        daytimeDAO.insert(daytime);
        recipe.setDaytime(daytime);
        recipeDAO.update(recipe);
        final Recipe byId = recipeDAO.findById(recipe.getID()).get();
        the(byId.getDaytime().getName()).shouldBeEqual(daytimeName);
    }

    /**
     * Postcondition: The season of the recipe is changed.
     */
    @Test
    public void testSeason() {
        final SeasonDAO seasonDAO = new SeasonDAO();
        Season season = new Season();
        String seasonName = "Spring";
        season.setName(seasonName);
        seasonDAO.insert(season);
        recipe.setSeason(season);
        recipeDAO.update(recipe);
        final Recipe byId = recipeDAO.findById(recipe.getID()).get();
        the(byId.getSeason().getName()).shouldBeEqual(seasonName);
    }

    /**
     * Postcondition: The nurture of the recipe is changed.
     */
    @Test
    public void testNurture() {
        NurtureDAO nurtureDAO = new NurtureDAO();
        Nurture nurture = new Nurture();
        String nurtureName = "High carb";
        nurture.setName(nurtureName);
        nurtureDAO.insert(nurture);
        recipe.setNurture(nurture);
        recipeDAO.update(recipe);
        final Recipe byId = recipeDAO.findById(recipe.getID()).get();
        the(byId.getNurture().getName()).shouldBeEqual(nurtureName);
    }
    /**
     * Postcondition: the database content is unchanged.
     */
    @Test
    public void testSource() {
        SourceDAO sourceDAO = new SourceDAO();
        Source source = new Source();
        String sourceName = "chefkoch.de";
        source.setName(sourceName);
        sourceDAO.insert(source);
        recipe.setSource(source);
        recipeDAO.update(recipe);
        final Recipe byId = recipeDAO.findById(recipe.getID()).get();
        the(byId.getSource().getName()).shouldBeEqual(sourceName);
    }

    @Test
    public void testGetIngredients() {
        recipe.add("Nudeln", 2d, "kg");
        recipe.add("Nüsse", 3d, "kleine Stück");
        recipeDAO.update(recipe);
        final Recipe byId = recipeDAO.findById(recipe.getID()).get();
        final List<String> ingredientNames = getIngredientsNames(byId);
        the(ingredientNames).shouldContain("Nudeln");
        the(ingredientNames).shouldContain("Paprika");
        the(ingredientNames).shouldNotContain("Schokolade");
    }

    @Test
    public void testDoubleIngredientAmount() {
        recipe.add("Erdbeeren", 2.5, "kg");
        recipe.add("Tomaten", 3.7, "kleine Stück");
        recipeDAO.update(recipe);
        final Recipe byId = recipeDAO.findById(recipe.getID()).get();
        final List<Double> amounts = getAmounts(byId);
        the(amounts).shouldContain(2.5);
        the(amounts).shouldContain(3.7);
    }

    @Test
    public void testNullDoubleIngredientAmount() {
        recipe.add("Salz", null, null);
        recipe.add("Tomaten", 3.7, "kleine Stück");
        recipeDAO.update(recipe);
        final Recipe byId = recipeDAO.findById(recipe.getID()).get();
        final List<Double> amounts = getAmounts(byId);
        the(amounts).shouldContain(null);
        the(amounts).shouldContain(3.7);
    }

    private List<String> getIngredientsNames(IRecipe recipe) {
        return recipe
            .getIngredients()
            .stream()
            .map(Triple::getLeft)
            .map(IIngredient::getName)
            .collect(Collectors.toList());
    }

    private List<Double> getAmounts(IRecipe recipe) {
        return recipe
            .getIngredients()
            .stream()
            .map(Triple::getMiddle)
            .collect(Collectors.toList());
    }

}
