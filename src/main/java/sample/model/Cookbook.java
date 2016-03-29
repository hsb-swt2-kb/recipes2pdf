package sample.model;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

/**
 * Created by czoeller on 26.03.16.
 */
@Table("cookbook")
public class Cookbook extends Model {
    static {
        validatePresenceOf("title");
    }
}
