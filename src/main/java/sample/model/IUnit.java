package sample.model;

import sample.model.activejdbc.Unit;

/**
 * Created by czoeller on 02.04.16.
 */
public interface IUnit {
    static IUnit getInstance() {
        return new Unit();
    }

    boolean saveIt();

    String getName();

    void setName(String name);

    void add(IRecipeIngredient recipeIngredient);
}

