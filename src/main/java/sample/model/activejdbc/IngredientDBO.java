package sample.model.activejdbc;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;
import sample.model.IIngredient;

/**
 * Created by czoeller on 28.03.16.
 */
@Table("ingredient")
public class IngredientDBO extends Model implements IIngredient {

    static {
        validatePresenceOf("name");
    }

    @Override
    public Long getID() {
        return this.getLongId();
    }

    @Override
    public void setID(Long id) {
        setId(id);
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
    public int compareTo(IIngredient o) {
        return getLongId().compareTo(o.getID());
    }
}
