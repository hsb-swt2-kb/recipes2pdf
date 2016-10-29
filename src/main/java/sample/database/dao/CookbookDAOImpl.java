package sample.database.dao;

import sample.model.Cookbook;
import sample.model.CookbookRecipe;
import sample.model.Recipe;

/**
 * Created by czoeller on 10.07.16.
 */
public class CookbookDAOImpl extends GenericDAOImpl<Cookbook, Integer> implements ICookbookDAO {
    public void addRecipe(Cookbook cookbook, Recipe recipe) {
        CookbookRecipe cookbookRecipe = new CookbookRecipe();
        cookbookRecipe.setCookbook(cookbook);
        cookbookRecipe.setRecipe(recipe);
        cookbook.getCookbookRecipes().add( cookbookRecipe );
    }
}
