package sample.model.activejdbc;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;
import sample.model.IIngredient;
import sample.model.IRecipeIngredient;

/**
 * Created by czoeller on 28.03.16.
 */
@Table("ingredient")
public class IngredientDBO extends Model implements IIngredient {
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
    public long getID() {
        return getLongId();
    }

    public void add(IRecipeIngredient recipeIngredient) {
        this.add((Model) recipeIngredient);
    }

    @Override
    public int compareTo(IIngredient o) {
        return getLongId().compareTo(o.getID());
    }
}
