package sample.model.activejdbc;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.Table;
import sample.model.IRecipeIngredient;

/**
 * Created by czoeller on 28.03.16.
 */
@Table("recipe_ingredient")
@BelongsTo(foreignKeyName = "unit_id", parent = Unit.class)
public class RecipeIngredient extends Model implements IRecipeIngredient {
    @Override
    public RecipeIngredient ccreateIt(Object... namesAndValues) {
        return RecipeIngredient.createIt(namesAndValues);
    }
}
