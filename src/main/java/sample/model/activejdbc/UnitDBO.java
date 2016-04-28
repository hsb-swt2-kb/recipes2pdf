package sample.model.activejdbc;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;
import sample.model.IRecipeIngredient;
import sample.model.IUnit;

/**
 * Created by czoeller on 02.04.16.
 */
@Table("unit")
public class UnitDBO extends Model implements IUnit {

    @Override
    public String getName() {
        return getString("name");
    }

    @Override
    public void setName(String name) {
        setString("name", name);
    }
}
