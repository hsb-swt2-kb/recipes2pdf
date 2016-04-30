package sample.model.activejdbc;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;
import sample.model.ISeason;

/**
 * Created by czoeller on 03.04.16.
 */
@Table("season")
public class SeasonDBO extends Model implements ISeason {
    @Override
    public String getName() {
        return getString("name");
    }

    @Override
    public void setName(String name) {
        setString("name", name);
    }
}
