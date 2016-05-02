package sample.model.activejdbc;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;
import sample.model.IIngredient;
import sample.model.IIdentifiable;

/**
 * Created by czoeller on 28.03.16.
 */
@Table("ingredient")
public class IngredientDBO extends Model implements IIngredient, IIdentifiable {
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
    public Long getID() {
        return getLongId();
    }

    @Override
    public void setID(Long id) {
        setId(id);
    }

    @Override
    public int compareTo(IIngredient o) {
        return getLongId().compareTo(o.getID());
    }
}
