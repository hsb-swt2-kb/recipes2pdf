package sample.model.activejdbc;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import sample.model.IRecipe;
import sample.model.Recipe;
import sample.model.RecipeDAO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.javalite.test.jspec.JSpec.the;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Created by czoeller on 08.04.16.
 */
public class RecipeTest extends ADatabaseTest {

    Recipe nudeln;
    @Mock
    RecipeDAO recipeDAO;

    @Before
    public void setUp() {
        recipeDAO = new RecipeDAO();
        when(recipeDAO.findFirst(any(String.class), any(String.class))).thenReturn( Optional.of( new Recipe(null) ) );
        final Optional<Recipe> nudeln = recipeDAO.findFirst("title = ?", "Nudeln mit Soße");
        this.nudeln = nudeln.orElseThrow(IllegalStateException::new);
    }

    @Test
    public void testTitle() {
        String title = "The title of the recipe.";
        nudeln.setTitle(title);
        recipeDAO.update(nudeln);
        final Recipe byId = recipeDAO.findById(nudeln.getID()).get();
        the(byId.getTitle()).shouldBeEqual(title);
    }

    @Test
    public void testText() {
        String text = "Description of the recipe. The steps etc.";
        nudeln.setText(text);
        recipeDAO.update(nudeln);
        final Recipe byId = recipeDAO.findById(nudeln.getID()).get();
        the(byId.getText()).shouldBeEqual(text);
    }

    @Test
    public void testPortions() {
        int portions = 3;
        nudeln.setPortions(3);
        recipeDAO.update(nudeln);
        final Recipe byId = recipeDAO.findById(nudeln.getID()).get();
        the(byId.getPortions()).shouldBeEqual(portions);
    }

    @Test
    public void testDuration() {
        int duration = 3;
        nudeln.setDuration(duration);
        recipeDAO.update(nudeln);
        final Recipe byId = recipeDAO.findById(nudeln.getID()).get();
        the(byId.getDuration()).shouldBeEqual(duration);
    }

    //TODO: @Test public void testImage() {}

    @Test
    public void testCalories() {
        int setCalories = 300;
        nudeln.setCalories(setCalories);
        recipeDAO.update(nudeln);
        final Recipe byId = recipeDAO.findById(nudeln.getID()).get();
        the(byId.getCalories()).shouldBeEqual(setCalories);
    }

/*    @Test
    public void testCategory() {
        ICategory category = ICategory.getInstance();
        String categoryName = "Example CategoryDBO";
        category.setName(categoryName);
        categoryDAO.update(category);
        nudeln.setCategory(category);
        recipeDAO.update(nudeln);
        final Recipe byId = recipeDAO.findById(nudeln.getID()).get();
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
        final Recipe byId = recipeDAO.findById(nudeln.getID()).get();
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
        final Recipe byId = recipeDAO.findById(nudeln.getID()).get();
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
        final Recipe byId = recipeDAO.findById(nudeln.getID()).get();
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
        final Recipe byId = recipeDAO.findById(nudeln.getID()).get();
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
        final Recipe byId = recipeDAO.findById(nudeln.getID()).get();
        the(byId.getNurture().getName()).shouldBeEqual(nurtureName);
    }*/

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
