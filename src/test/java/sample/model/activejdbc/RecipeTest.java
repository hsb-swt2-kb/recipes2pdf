package sample.model.activejdbc;

import org.junit.Test;
import sample.model.IRecipe;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.javalite.test.jspec.JSpec.the;

/**
 * Created by czoeller on 08.04.16.
 */
public class RecipeTest extends ADatabaseTest {

    @Test
    public void testGetIngredients() {
        final RecipeRepository recipeRepository = new RecipeRepository();
        final Optional<IRecipe> nudeln = recipeRepository.findFirst("title = ?", "Nudeln mit Soße");
        nudeln.orElseThrow(IllegalStateException::new);

        final List<String> ingredientNames = getIngredientsNames(nudeln.get());
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
