package sample.model.activejdbc;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;
import sample.model.ICourse;

/**
 * Created by czoeller on 03.04.16.
 */
@Table("course")
public class Course extends Model implements ICourse {
    @Override
    public String getName() {
        return getString("name");
    }

    @Override
    public void setName(String name) {
        setString("name", name);
    }
}
