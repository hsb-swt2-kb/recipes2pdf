package sample.model;

import sample.model.activejdbc.Ingredient;

/**
 * Created by czoeller on 02.04.16.
 */
public interface IIngredient extends Comparable<IIngredient> {
    static IIngredient getInstance() { return new Ingredient(); }

    String getName();
    void setName(String name);
    long getID();
    boolean saveIt();
    boolean delete();

    void add(IRecipeIngredient recipeIngredient);
}
