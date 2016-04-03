package sample.model.activejdbc;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;
import sample.model.IDaytime;

/**
 * Created by czoeller on 03.04.16.
 */
@Table("daytime")
public class Daytime extends Model implements IDaytime {
    @Override
    public String getName() {
        return getString("name");
    }

    @Override
    public void setName(String name) {
        setString("name", name);
    }
}
