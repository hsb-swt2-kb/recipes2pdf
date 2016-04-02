package sample.model;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

/**
 * Created by czoeller on 28.03.16.
 */
@Table("ingredient")
public class Ingredient extends Model implements IIngredient {
    static {
        validatePresenceOf("name");
    }

    @Override
    public void setName(String name) {
        this.set("name", name);
    }

    @Override
    public void add(IRecipeIngredient recipeIngredient) {
        this.add((Model) recipeIngredient);
    }
}
