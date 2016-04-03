package sample.model.activejdbc;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;
import sample.model.*;

import java.util.Optional;

/**
 * Created by czoeller on 25.03.16.
 */
@Table("recipe")
public class Recipe extends Model implements IRecipe {

    static {
        validatePresenceOf("title");
    }

    @Override
    public void add(IRecipeIngredient recipeIngredient) {
        this.add((Model) recipeIngredient);
    }

    @Override
    public void add(String ingredientName, int amount, String unitName) {
        IIngredientRepository ingredientRepository = new IngredientRepository();
        IUnitRepository unitRepository = new UnitRepository();

        Optional<IIngredient> ingredient = ingredientRepository.findFirst("name = ?", ingredientName);
        Optional<IUnit> unit = unitRepository.findFirst("name = ?", unitName);

        // Create Many2Many Relation Recipe<---recipeIngredient--->Ingredient
        final RecipeIngredient recipeIngredient = RecipeIngredient.createIt("amount", amount);

        // set recipeIngredient ends
        // Create ingredient on the fly if it was not there yet
        ingredient.orElseGet(() -> {
            return (IIngredient) Ingredient.createIt("name", ingredientName + "else");
        }).add(recipeIngredient);
        //ingredient.orElseGet( Ingredient.createIt("name", ingredientName + "else") ).add(recipeIngredient);
        this.add((Model) recipeIngredient);

        // Create HasMany Relation unit ---< recipe_ingredient
        // Create unit on the fly if it was not there yet
        unit.orElseGet(() -> {
            return (IUnit) Unit.createIt("name", unitName);
        }).add(recipeIngredient);
    }

    public String getTitle() {
        return getString("title");
    }

    public void setTitle(String title) {
        set("title", title);
    }

}
