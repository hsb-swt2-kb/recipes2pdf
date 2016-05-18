package sample.database.dbo;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;
import sample.model.INurture;

/**
 * Created by czoeller on 03.04.16.
 */
@Table("nurture")
public class NurtureDBO extends Model implements INurture {

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
}
