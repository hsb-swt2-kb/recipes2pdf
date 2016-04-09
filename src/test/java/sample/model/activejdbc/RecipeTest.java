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

        final List<String> ingredientNames = nudeln.get()
            .getIngredients()
            .keySet()
            .stream()
            .map(iIngredient -> iIngredient.getName())
            .collect(Collectors.toList());

        the(ingredientNames).shouldContain("Nudeln");
        the(ingredientNames).shouldContain("Paprika");
        the(ingredientNames).shouldNotContain("Schokolade");
    }
}
