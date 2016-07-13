package sample.database.dao;

import sample.model.Recipe;

/**
 * Created by czoeller on 11.07.16.
 */
public interface IRecipeDAO extends IGenericDAO<Recipe, Integer> {
    void add(Recipe recipe, String ingredientName, double amount, String unitName);
}
