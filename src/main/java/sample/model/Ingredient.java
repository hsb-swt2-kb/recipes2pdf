package sample.model;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

/**
 * Created by czoeller on 28.03.16.
 */
@Table("ingredient")
public class Ingredient extends Model {
    static {
        validatePresenceOf("name");
    }
}
