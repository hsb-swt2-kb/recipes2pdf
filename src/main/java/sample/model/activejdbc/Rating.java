package sample.model.activejdbc;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;
import sample.model.IRating;

/**
 * Created by czoeller on 03.04.16.
 */
@Table("rating")
public class Rating extends Model implements IRating {
    @Override
    public String getName() {
        return getString("name");
    }

    @Override
    public void setName(String name) {
        setString("name", name);
    }
}
