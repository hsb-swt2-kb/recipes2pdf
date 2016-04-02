package sample.model;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

/**
 * Created by czoeller on 28.03.16.
 */
@Table("recipe_ingredient")
public class RecipeIngredient extends Model implements IRecipeIngredient {

    @Override
    public RecipeIngredient ccreateIt(Object... namesAndValues) {
        return RecipeIngredient.createIt(namesAndValues);
    }

    @Override
    public void add(IIngredient ingredient) {
        ((Model) this).add((Model) ingredient);
    }
}
