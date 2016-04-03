package sample.model.activejdbc;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;
import sample.model.IIngredient;
import sample.model.IRecipeIngredient;

/**
 * Created by czoeller on 28.03.16.
 */
@Table("ingredient")
public class Ingredient extends Model implements IIngredient {
    static {
        validatePresenceOf("name");
    }

    @Override
    public String getName() {
        return getString("name");
    }

    @Override
    public void setName(String name) {
        setString("name", name);
    }

    @Override
    public void add(IRecipeIngredient recipeIngredient) {
        this.add((Model) recipeIngredient);
    }
}
