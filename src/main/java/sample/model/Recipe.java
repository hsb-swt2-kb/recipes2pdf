package sample.model;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

/**
 * Created by czoeller on 25.03.16.
 */
@Table("recipe")
public class Recipe extends Model {

    static {
        validatePresenceOf("title");
    }

    public void setTitle(String title){
        set("title", title);
    }

    public String getTitle() {
        return getString("title");
    }
}
