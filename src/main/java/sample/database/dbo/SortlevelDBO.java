package sample.database.dbo;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Many2Many;
import org.javalite.activejdbc.annotations.Table;
import sample.model.ISortlevel;

/**
 * Created by czoeller on 03.04.16.
 */
@Table("sortlevel")
@Many2Many(other = CookbookDBO.class, join = "cookbook_sortlevel", sourceFKName = "sortlevel_id", targetFKName = "cookbook_id")
public class SortlevelDBO extends Model implements ISortlevel {

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
