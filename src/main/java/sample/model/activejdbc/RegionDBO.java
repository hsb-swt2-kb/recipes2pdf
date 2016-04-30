package sample.model.activejdbc;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;
import sample.model.IRegion;

/**
 * Created by czoeller on 03.04.16.
 */
@Table("region")
public class RegionDBO extends Model implements IRegion {
    @Override
    public String getName() {
        return getString("name");
    }

    @Override
    public void setName(String name) {
        setString("name", name);
    }
}
