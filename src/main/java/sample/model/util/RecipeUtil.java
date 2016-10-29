package sample.model.util;

import sample.model.Recipe;

/**
 * Created by czoeller on 13.07.16.
 */
public class RecipeUtil {
    public static boolean isRecipeEmpty(Recipe recipe) {
        return recipe.getTitle()       == null &&
            recipe.getIngredients() == null &&
            recipe.getText()        == null &&
            recipe.getTitle().isEmpty()     &&
            recipe.getText().isEmpty()      &&
            recipe.getIngredients().isEmpty();
    }

    public static boolean isRecipeIncomplete(Recipe recipe) {
        return recipe.getTitle() == null ||
            recipe.getIngredients() == null ||
            recipe.getText() == null ||
            recipe.getTitle().isEmpty() ||
            recipe.getText().isEmpty() ||
            recipe.getIngredients().isEmpty();
    }
}
