package sample.model;

import sample.model.dummy.DummyRecipe;

import java.util.Map;

/**
 * Created by kai on 07.04.16.
 */
public interface IRecipe {
    static DummyRecipe getInstance() {
        return new DummyRecipe();
    }

    boolean saveIt();

    String getTitle();

    void setTitle(String title);

    void add(String ingredientName, int amount, String unitName);

    Long getID();

    Map<IIngredient, Map<Integer, IUnit>> getIngredients();

    String getCategory();

    int getCategoryNumber();

    String getRecipeText();
}
