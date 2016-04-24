package sample.model.activejdbc;

import org.junit.Before;
import org.junit.Test;
import sample.model.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.javalite.test.jspec.JSpec.the;

/**
 * Created by czoeller on 08.04.16.
 */
public class RecipeTest extends ADatabaseTest {

    IRecipe nudeln;

    @Before
    public void setUp() {
        final RecipeRepository recipeRepository = new RecipeRepository();
        final Optional<IRecipe> nudeln = recipeRepository.findFirst("title = ?", "Nudeln mit Soße");
        this.nudeln = nudeln.orElseThrow(IllegalStateException::new);
    }

    @Test
    public void testTitle() {
        String title = "The title of the recipe.";
        nudeln.setTitle(title);
        nudeln.saveIt();
        final Recipe byId = Recipe.findById(nudeln.getID());
        the(byId.getTitle()).shouldBeEqual(title);
    }

    @Test
    public void testText() {
        String text = "Description of the recipe. The steps etc.";
        nudeln.setText(text);
        nudeln.saveIt();
        final Recipe byId = Recipe.findById(nudeln.getID());
        the(byId.getText()).shouldBeEqual(text);
    }

    @Test
    public void testPortions() {
        int portions = 3;
        nudeln.setPortions(3);
        nudeln.saveIt();
        final Recipe byId = Recipe.findById(nudeln.getID());
        the(byId.getPortions()).shouldBeEqual(portions);
    }

    @Test
    public void testDuration() {
        int duration = 3;
        nudeln.setDuration(duration);
        nudeln.saveIt();
        final Recipe byId = Recipe.findById(nudeln.getID());
        the(byId.getDuration()).shouldBeEqual(duration);
    }

    //TODO: @Test public void testImage() {}

    @Test
    public void testCalories() {
        int setCalories = 300;
        nudeln.setCalories(setCalories);
        nudeln.saveIt();
        final Recipe byId = Recipe.findById(nudeln.getID());
        the(byId.getCalories()).shouldBeEqual(setCalories);
    }

    @Test
    public void testCategory() {
        ICategory category = ICategory.getInstance();
        String categoryName = "Example Category";
        category.setName(categoryName);
        category.saveIt();
        nudeln.setCategory(category);
        nudeln.saveIt();
        final Recipe byId = Recipe.findById(nudeln.getID());
        the(byId.getCategory().getName()).shouldBeEqual(categoryName);
    }

    @Test
    public void testCourse() {
        ICourse course = ICourse.getInstance();
        String courseName = "main course";
        course.setName(courseName);
        course.saveIt();
        nudeln.setCourse(course);
        nudeln.saveIt();
        final Recipe byId = Recipe.findById(nudeln.getID());
        the(byId.getCourse().getName()).shouldBeEqual(courseName);
    }

    @Test
    public void testRegion() {
        IRegion region = IRegion.getInstance();
        String regionName = "Spain";
        region.setName(regionName);
        region.saveIt();
        nudeln.setRegion(region);
        nudeln.saveIt();
        final Recipe byId = Recipe.findById(nudeln.getID());
        the(byId.getRegion().getName()).shouldBeEqual(regionName);
    }

    @Test
    public void testDaytime() {
        IDaytime daytime = IDaytime.getInstance();
        String daytimeName = "Breakfast";
        daytime.setName(daytimeName);
        daytime.saveIt();
        nudeln.setDaytime(daytime);
        nudeln.saveIt();
        final Recipe byId = Recipe.findById(nudeln.getID());
        the(byId.getDaytime().getName()).shouldBeEqual(daytimeName);
    }

    @Test
    public void testSeason() {
        ISeason season = ISeason.getInstance();
        String seasonName = "Spring";
        season.setName(seasonName);
        season.saveIt();
        nudeln.setSeason(season);
        nudeln.saveIt();
        final Recipe byId = Recipe.findById(nudeln.getID());
        the(byId.getSeason().getName()).shouldBeEqual(seasonName);
    }

    @Test
    public void testNurture() {
        INurture nurture = INurture.getInstance();
        String nurtureName = "High carb";
        nurture.setName(nurtureName);
        nurture.saveIt();
        nudeln.setNurture(nurture);
        nudeln.saveIt();
        final Recipe byId = Recipe.findById(nudeln.getID());
        the(byId.getNurture().getName()).shouldBeEqual(nurtureName);
    }

    @Test
    public void testGetIngredients() {
        final List<String> ingredientNames = getIngredientsNames(nudeln);
        the(ingredientNames).shouldContain("Nudeln");
        the(ingredientNames).shouldContain("Paprika");
        the(ingredientNames).shouldNotContain("Schokolade");
    }

    private List<String> getIngredientsNames(IRecipe nudeln) {
        return nudeln
            .getIngredients()
            .keySet()
            .stream()
            .map(iIngredient -> iIngredient.getName())
            .collect(Collectors.toList());
    }

}
