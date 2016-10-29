package sample.database.dao;

import sample.model.Cookbook;
import sample.model.Recipe;

/**
 * Created by czoeller on 10.07.16.
 */
public interface ICookbookDAO extends IGenericDAO<Cookbook, Integer> {
    void addRecipe(Cookbook cookbook, Recipe recipe);
}
