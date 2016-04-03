package sample.model;

import sample.model.activejdbc.RecipeIngredient;

/**
 * Created by czoeller on 02.04.16.
 */
public interface IRecipeIngredient {
    static IRecipeIngredient getInstance() {
        return new RecipeIngredient();
    }
    IRecipeIngredient ccreateIt(Object... namesAndValues);
}
