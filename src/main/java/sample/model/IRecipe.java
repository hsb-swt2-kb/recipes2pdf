package sample.model;

import sample.model.activejdbc.Recipe;

import java.util.Map;

/**
 * Created by czoeller on 16.04.16.
 */
public interface IRecipe {
    static IRecipe getInstance() {
        return new Recipe();
    }
    boolean saveIt();

    String getTitle();
    void setTitle(String title);

    Long getID();

    Map<IIngredient, Map<Integer, IUnit>> getIngredients();
    void add(String ingredientName, int amount, String unitName);

    String getCategory();

    int getCategoryNumber();

    String getRecipeText();

    void setImage(byte[] image);
}
