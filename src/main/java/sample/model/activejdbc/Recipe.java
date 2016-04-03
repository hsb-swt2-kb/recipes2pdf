package sample.model.activejdbc;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.BelongsToParents;
import org.javalite.activejdbc.annotations.Many2Many;
import org.javalite.activejdbc.annotations.Table;
import sample.model.*;

import java.util.Optional;

/**
 * Created by czoeller on 25.03.16.
 */
@Table("recipe")
@Many2Many(other = Cookbook.class, join = "cookbook_recipe", sourceFKName = "recipe_id", targetFKName = "cookbook_id")
@BelongsToParents({
    @BelongsTo(foreignKeyName = "category_id", parent = Category.class),
    @BelongsTo(foreignKeyName = "course_id", parent = Course.class),
    @BelongsTo(foreignKeyName = "region_id", parent = Region.class),
    @BelongsTo(foreignKeyName = "daytime_id", parent = Daytime.class),
    @BelongsTo(foreignKeyName = "season_id", parent = Season.class),
    @BelongsTo(foreignKeyName = "nurture_id", parent = Nurture.class)
})
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
        ingredient.orElseGet(() -> (IIngredient) Ingredient.createIt("name", ingredientName + "else")).add(recipeIngredient);
        //ingredient.orElseGet( Ingredient.createIt("name", ingredientName + "else") ).add(recipeIngredient);
        this.add((Model) recipeIngredient);

        // Create HasMany Relation unit ---< recipe_ingredient
        // Create unit on the fly if it was not there yet
        unit.orElseGet(() -> (IUnit) Unit.createIt("name", unitName)).add(recipeIngredient);
    }

    public String getTitle() {
        return getString("title");
    }

    public void setTitle(String title) {
        set("title", title);
    }

}
