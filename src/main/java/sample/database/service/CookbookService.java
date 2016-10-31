package sample.database.service;

import sample.database.dao.IGenericDAO;
import sample.model.Cookbook;
import sample.model.CookbookRecipe;
import sample.model.Recipe;

import javax.inject.Inject;

/**
 * Created by noex_ on 30.10.2016.
 */
public class CookbookService {

    @Inject
    private IGenericDAO<Cookbook, Integer> cookbookDAO;

    public void removeRecipeFromCookbook(Cookbook cookbook, Recipe recipe) {
        cookbook.getCookbookRecipes().stream().filter(cookbookRecipe -> cookbookRecipe.getRecipe().equals(recipe)).forEach(cookbookRecipe -> cookbook.getCookbookRecipes().remove(cookbookRecipe));
        cookbookDAO.update(cookbook);
    }

    public void addRecipeToCookbook(Cookbook cookbook, Recipe recipe) {
        final CookbookRecipe cookbookRecipe = new CookbookRecipe();
        cookbookRecipe.setCookbook(cookbook);
        cookbookRecipe.setRecipe(recipe);

        cookbook.getCookbookRecipes().add( cookbookRecipe );
        recipe.getCookbookRecipes().add(cookbookRecipe);
        cookbookDAO.update(cookbook);
    }
}
