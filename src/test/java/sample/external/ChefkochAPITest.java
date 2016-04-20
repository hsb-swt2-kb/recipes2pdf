package sample.external;

import org.junit.Before;
import org.junit.Test;
import sample.model.IRecipe;
import sample.model.fake.Recipe;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.javalite.test.jspec.JSpec.the;

/**
 * Created by czoeller on 16.04.16.
 */
public class ChefkochAPITest {

    private ChefkochAPI chefKochAPI;

    @Before
    public void setUp() throws Throwable {
        this.chefKochAPI = new ChefkochAPI();
        this.chefKochAPI.setRecipe( new Recipe() );
    }

    @Test
    public void getShowId() {
        final Optional<String> showID = chefKochAPI.search("Zucchini - Lasagne");
        the(showID.get()).shouldBeEqual("1616691268862802");
    }

    @Test
    public void notFoundRecipesReturnEmpty() {
        final Optional<String> showID = chefKochAPI.search("Unknown Recipe 21020222");
        the(showID.isPresent()).shouldBeFalse();
    }

    @Test
    public void getRecipe() throws Exception {
        final Optional<String> showID = chefKochAPI.search("Zucchini - Lasagne");
        final Optional<IRecipe> recipe = chefKochAPI.findById( showID.get() );
        the(recipe.get().getTitle()).shouldBeEqual("Zucchini - Lasagne");
    }

    @Test
    public void theParsedRecipeHasProperIngredients() {
        final Optional<String> showID = chefKochAPI.search("Zucchini - Lasagne");
        final Optional<IRecipe> recipe = chefKochAPI.findById( showID.get() );
        final List<String> ingredientNames = extractTitles(recipe.get());
        the(ingredientNames).shouldContain("Zucchini");
        the(ingredientNames).shouldContain("Hackfleisch");
        the(ingredientNames).shouldContain("Zwiebel(n)");
    }

    List<String> extractTitles(IRecipe recipe) {
        return recipe
            .getIngredients()
            .keySet()
            .stream()
            .map(iIngredient -> iIngredient.getName())
            .collect(Collectors.toList());
    }
}
