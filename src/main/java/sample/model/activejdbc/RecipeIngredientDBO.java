package sample.model.activejdbc;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.Table;
import sample.model.IRecipeIngredient;

/**
 * Created by czoeller on 28.03.16.
 */
@Table("recipe_ingredient")
@BelongsTo(foreignKeyName = "unit_id", parent = UnitDBO.class)
public class RecipeIngredientDBO extends Model implements IRecipeIngredient {

    @Override
    public Long getID() {
        return this.getLongId();
    }

    @Override
    public void setID(Long id) {
        setId(id);
    }

}
