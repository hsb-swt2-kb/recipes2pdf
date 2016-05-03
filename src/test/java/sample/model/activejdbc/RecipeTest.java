package sample.model.activejdbc;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import sample.model.*;
import sample.model.dao.DaytimeDAO;
import sample.model.dao.NurtureDAO;
import sample.model.dao.RecipeDAO;
import sample.model.dao.SeasonDAO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.javalite.test.jspec.JSpec.the;

/**
 * Created by czoeller on 08.04.16.
 */
@RunWith(MockitoJUnitRunner.class)
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

    @Test
    public void testTitle() {
        String title = "The title of the recipe.";
        recipe.setTitle(title);
        recipeDAO.update(recipe);
        final Recipe byId = recipeDAO.findById(recipe.getID()).get();
        the(byId.getTitle()).shouldBeEqual(title);
    }

    @Test
    public void testText() {
        String text = "Description of the recipe. The steps etc.";
        recipe.setText(text);
        recipeDAO.update(recipe);
        final Recipe byId = recipeDAO.findById(recipe.getID()).get();
        the(byId.getText()).shouldBeEqual(text);
    }

    @Test
    public void testPortions() {
        int portions = 3;
        recipe.setPortions(3);
        recipeDAO.update(recipe);
        final Recipe byId = recipeDAO.findById(recipe.getID()).get();
        the(byId.getPortions()).shouldBeEqual(portions);
    }

    @Test
    public void testDuration() {
        int duration = 3;
        recipe.setDuration(duration);
        recipeDAO.update(recipe);
        final Recipe byId = recipeDAO.findById(recipe.getID()).get();
        the(byId.getDuration()).shouldBeEqual(duration);
    }

    //TODO: @Test public void testImage() {}

    @Test
    public void testCalories() {
        int setCalories = 300;
        recipe.setCalories(setCalories);
        recipeDAO.update(recipe);
        final Recipe byId = recipeDAO.findById(recipe.getID()).get();
        the(byId.getCalories()).shouldBeEqual(setCalories);
    }

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

   /* @Test
    public void testCourse() {
        CourseDAO courseDAO = new CourseDAO();
        Course course = new Course();
        String courseName = "main course";
        course.setName(courseName);
        courseDAO.insert(course);
        recipe.setCourse(course);
        recipeDAO.update(recipe);
        final Recipe byId = recipeDAO.findById(recipe.getID()).get();
        the(byId.getCourse().getName()).shouldBeEqual(courseName);
    }
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

    @Test
    public void testGetIngredients() {
        recipe.add("Nudeln", 2, "kg");
        recipe.add("Paprika", 3, "kg");
        recipeDAO.update(recipe);
        final Recipe recipe = recipeDAO.findById(1L).get();
        final List<String> ingredientNames = getIngredientsNames(recipe);
        the(ingredientNames).shouldContain("Nudeln");
        the(ingredientNames).shouldContain("Paprika");
        the(ingredientNames).shouldNotContain("Schokolade");
    }

    private List<String> getIngredientsNames(IRecipe nudeln) {
        return nudeln
            .getIngredients()
            .stream()
            .map(iIngredientIntegerIUnitTriple -> iIngredientIntegerIUnitTriple.getLeft() )
            .map(iIngredient -> iIngredient.getName())
            .collect(Collectors.toList());
    }

}
