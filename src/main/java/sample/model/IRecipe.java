package sample.model;

import sample.model.activejdbc.Recipe;

import java.util.Map;

/**
 * Created by czoeller on 01.04.16.
 */
public interface IRecipe {
    static IRecipe getInstance() {
        return new Recipe();
    }

    boolean saveIt();
    String getTitle();
    long getID();
    void setTitle(String title);
    void add(IRecipeIngredient recipeIngredient);
    void add(String ingredientName, int amount, String unitName);
    Map<IIngredient, Map<Integer, IUnit>> getIngredients();
    byte[] getImage();
}
