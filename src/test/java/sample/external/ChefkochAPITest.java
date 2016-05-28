package sample.external;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import sample.model.IRecipe;
import sample.model.fake.Recipe;
import sample.util.ResourceLoader;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.javalite.test.jspec.JSpec.the;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

/**
 * Created by czoeller on 16.04.16.
 */
@RunWith(MockitoJUnitRunner.class)
public class ChefkochAPITest {

    private final String EXAMPLE_RECIPE_TITLE = "Zucchini - Lasagne";
    private final String EXAMPLE_RECIPE_ID = "1616691268862802";

    @Spy
    private ChefkochAPI chefKochAPI;

    @Before
    public void setUp() throws Throwable {
        this.chefKochAPI.setRecipe( new Recipe() );

        JSONObject search = readJSON("/sample/parser/ChefkochAPISearchResponse.json");
        JSONObject recipe_detail = readJSON("/sample/parser/ChefkochAPIDetailResponse.json");
        doNothing().when(this.chefKochAPI).downloadAndSetImage(any(String.class));
        doReturn(search).when(this.chefKochAPI).query(eq(ChefkochAPI.SEARCH_API), any(String.class), eq(EXAMPLE_RECIPE_TITLE));
        doReturn(new JSONObject()).when(this.chefKochAPI).query(eq(ChefkochAPI.RECIPE_DETAIL_API), any(String.class), not(eq(EXAMPLE_RECIPE_TITLE)));
        doReturn(recipe_detail).when(this.chefKochAPI).query(eq(ChefkochAPI.RECIPE_DETAIL_API), any(String.class), eq(EXAMPLE_RECIPE_ID));
        doReturn(new JSONObject()).when(this.chefKochAPI).query(eq(ChefkochAPI.RECIPE_DETAIL_API), any(String.class), not(eq(EXAMPLE_RECIPE_ID)));
    }

    private JSONObject readJSON(String packagepath) {
        final String content = ResourceLoader.loadFileContents(this.getClass(), packagepath);
        final JSONObject json = new JSONObject(content);
        return json;
    }

    @Test
    public void getShowId() {
        final Optional<String> showID = chefKochAPI.search(EXAMPLE_RECIPE_TITLE);
        the(showID.get()).shouldBeEqual(EXAMPLE_RECIPE_ID);
    }

    @Test
    public void notFoundRecipesReturnEmpty() {
        final Optional<String> showID = chefKochAPI.search("Unknown Recipe 21020222");
        the(showID.isPresent()).shouldBeFalse();
    }

    @Test
    public void getRecipe() throws Exception {
        final Optional<String> showID = chefKochAPI.search(EXAMPLE_RECIPE_TITLE);
        final Optional<IRecipe> recipe = chefKochAPI.findById( showID.get() );
        the(recipe.get().getTitle()).shouldBeEqual(EXAMPLE_RECIPE_TITLE);
    }

    @Test
    public void theParsedRecipeHasProperIngredients() {
        final Optional<String> showID = chefKochAPI.search(EXAMPLE_RECIPE_TITLE);
        final Optional<IRecipe> recipe = chefKochAPI.findById( showID.get() );
        final List<String> ingredientNames = extractIngredientNames(recipe.get());
        the(ingredientNames).shouldContain("Zucchini");
        the(ingredientNames).shouldContain("Hackfleisch");
        the(ingredientNames).shouldContain("Zwiebel(n)");
    }

    List<String> extractIngredientNames(IRecipe recipe) {
        return recipe
            .getIngredients()
            .keySet()
            .stream()
            .map(iIngredient -> iIngredient.getName())
            .collect(Collectors.toList());
    }
}
