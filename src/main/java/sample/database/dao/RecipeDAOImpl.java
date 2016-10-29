package sample.database.dao;

import sample.model.Ingredient;
import sample.model.Recipe;
import sample.model.RecipeIngredient;
import sample.model.Unit;

import javax.inject.Inject;

/**
 * Created by czoeller on 11.07.16.
 */
public class RecipeDAOImpl extends GenericDAOImpl<Recipe, Integer> implements IRecipeDAO {

    @Inject
    private IIngredientDAO ingredientDAO;
    @Inject
    private IUnitDAO unitDAO;

    @Override
    public void add(final Recipe recipe, final String ingredientName, final double amount, String unitName) {

        Ingredient ingredient = new Ingredient();
        ingredient.setName(ingredientName);
        ingredient = ingredientDAO.findOrCreate(ingredient, "name", ingredientName);

        Unit unit = new Unit();
        unit.setName(unitName);
        unit = unitDAO.findOrCreate(unit, "name", unitName);

        boolean alreadyContains = recipe.getIngredients().stream().map(Ingredient::getName).filter(name -> name.equals(ingredientName)).findAny().isPresent();//recipe.getRecipeIngredients().contains(recipeIngredient);

        if( !alreadyContains ) {
            final RecipeIngredient recipeIngredient = new RecipeIngredient();
            recipeIngredient.setAmount(amount);
            recipeIngredient.setUnit(unit);
            recipeIngredient.setIngredient(ingredient);
            recipeIngredient.setRecipe(recipe);
            recipe.getRecipeIngredients().add(recipeIngredient);
        }
    }
}
