package sample.model.activejdbc;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
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
@RunWith(MockitoJUnitRunner.class)
public class RecipeTest extends ADatabaseTest {

    Recipe recipe;
    @Mock
    RecipeDAO recipeDAO;

    @Before
    public void setUp() {
        final Recipe recipe = new Recipe();
        recipe.setID(1L);
        when(recipeDAO.findFirst(any(String.class), any())).thenReturn( Optional.of(recipe) );
        when(recipeDAO.findById(1L)).thenReturn(  Optional.of(recipe)  );
        when(recipeDAO.update(any(Recipe.class))).then(invocation -> { this.recipe = invocation.getArgumentAt(0, Recipe.class); return true; } );
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

/*    @Test
    public void testCategory() {
        ICategory category = ICategory.getInstance();
        String categoryName = "Example CategoryDBO";
        category.setName(categoryName);
        categoryDAO.update(category);
        recipe.setCategory(category);
        recipeDAO.update(recipe);
        final Recipe byId = recipeDAO.findById(recipe.getID()).get();
        the(byId.getCategory().getName()).shouldBeEqual(categoryName);
    }

    @Test
    public void testCourse() {
        ICourse course = ICourse.getInstance();
        String courseName = "main course";
        course.setName(courseName);
        course.saveIt();
        recipe.setCourse(course);
        recipe.saveIt();
        final Recipe byId = recipeDAO.findById(recipe.getID()).get();
        the(byId.getCourse().getName()).shouldBeEqual(courseName);
    }

    @Test
    public void testRegion() {
        IRegion region = IRegion.getInstance();
        String regionName = "Spain";
        region.setName(regionName);
        region.saveIt();
        recipe.setRegion(region);
        recipe.saveIt();
        final Recipe byId = recipeDAO.findById(recipe.getID()).get();
        the(byId.getRegion().getName()).shouldBeEqual(regionName);
    }

    @Test
    public void testDaytime() {
        IDaytime daytime = IDaytime.getInstance();
        String daytimeName = "Breakfast";
        daytime.setName(daytimeName);
        daytime.saveIt();
        recipe.setDaytime(daytime);
        recipe.saveIt();
        final Recipe byId = recipeDAO.findById(recipe.getID()).get();
        the(byId.getDaytime().getName()).shouldBeEqual(daytimeName);
    }

    @Test
    public void testSeason() {
        ISeason season = ISeason.getInstance();
        String seasonName = "Spring";
        season.setName(seasonName);
        season.saveIt();
        recipe.setSeason(season);
        recipe.saveIt();
        final Recipe byId = recipeDAO.findById(recipe.getID()).get();
        the(byId.getSeason().getName()).shouldBeEqual(seasonName);
    }

    @Test
    public void testNurture() {
        INurture nurture = INurture.getInstance();
        String nurtureName = "High carb";
        nurture.setName(nurtureName);
        nurture.saveIt();
        recipe.setNurture(nurture);
        recipe.saveIt();
        final Recipe byId = recipeDAO.findById(recipe.getID()).get();
        the(byId.getNurture().getName()).shouldBeEqual(nurtureName);
    }*/

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
            .keySet()
            .stream()
            .map(iIngredient -> iIngredient.getName())
            .collect(Collectors.toList());
    }

}
