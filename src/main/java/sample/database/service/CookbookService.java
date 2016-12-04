package sample.database.service;

import sample.database.dao.IGenericDAO;
import sample.model.Cookbook;
import sample.model.CookbookRecipe;
import sample.model.Recipe;

import javax.inject.Inject;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by noex_ on 30.10.2016.
 */
public class CookbookService {

    @Inject
    private IGenericDAO<Cookbook, Integer> cookbookDAO;
    @Inject
    private IGenericDAO<Recipe, Integer> recipeDAO;
    @Inject
    private IGenericDAO<CookbookRecipe, Integer> cookbookRecipeDAO;

    public void removeRecipeFromCookbook(Cookbook cookbook, Recipe recipe) {
        cookbook = cookbookDAO.update(cookbook);
        recipe = recipeDAO.update(recipe);
        final Recipe finalRecipe = recipe;
        final Cookbook finalCookbook = cookbook;
        final Set<CookbookRecipe> toBeDeleted = cookbook.getCookbookRecipes()
                                                        .stream()
                                                        .filter(cookbookRecipe -> cookbookRecipe.getRecipe()
                                                                                                .equals(finalRecipe))
                                                        .collect(Collectors.toSet());
        toBeDeleted.forEach(cookbookRecipe -> {
            finalCookbook.getCookbookRecipes()
                         .remove(cookbookRecipe);
            cookbookRecipe.getRecipe()
                          .getCookbookRecipes()
                          .remove(cookbookRecipe);
            cookbookRecipeDAO.remove(cookbookRecipe);
        });
        cookbookDAO.update(cookbook);
    }

    public void addRecipeToCookbook(Cookbook cookbook, Recipe recipe) {
        cookbook = cookbookDAO.update(cookbook);
        recipe = recipeDAO.update(recipe);

        final CookbookRecipe cookbookRecipe = new CookbookRecipe();
        cookbookRecipe.setCookbook(cookbook);
        cookbookRecipe.setRecipe(recipe);

        cookbook.getCookbookRecipes().add( cookbookRecipe );
        recipe.getCookbookRecipes().add(cookbookRecipe);
        cookbookDAO.update(cookbook);
    }

}
