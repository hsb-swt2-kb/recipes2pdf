package sample.database.dao;

import sample.model.Ingredient;
import sample.model.Recipe;
import sample.model.RecipeIngredient;
import sample.model.Unit;

import javax.inject.Inject;
import java.util.Optional;

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

        final RecipeIngredient recipeIngredient = new RecipeIngredient();
        final Optional<Ingredient> persistedIngredient = ingredientDAO.findFirst("name", ingredientName);
        final Optional<Unit> persistedUnit = unitDAO.findFirst("name", unitName);

        if( !persistedIngredient.isPresent() ) {
            final Ingredient ingredient = new Ingredient();
            ingredient.setName(ingredientName);
            ingredientDAO.add(ingredient);
            recipeIngredient.setIngredient(ingredient);
        } else {
            recipeIngredient.setIngredient( persistedIngredient.get() );
            persistedIngredient.get().getRecipeIngredients().add(recipeIngredient);
        }

        if( !persistedUnit.isPresent() ) {
            final Unit unit = new Unit();
            unit.setName(unitName);
            unitDAO.add(unit);
            recipeIngredient.setUnit(unit);
        } else {
            recipeIngredient.setUnit( persistedUnit.get() );
            persistedUnit.get().getRecipeIngredients().add(recipeIngredient);
        }

        recipeIngredient.setAmount(amount);
        recipeIngredient.setRecipe(recipe);
        recipe.getRecipeIngredients().add(recipeIngredient);
        recipeIngredient.setRecipe(recipe);
    }
}
