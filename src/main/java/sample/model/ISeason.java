package sample.model;

import sample.model.activejdbc.Season;

/**
 * Created by czoeller on 03.04.16.
 */
public interface ISeason {
    static ISeason getInstance() {
        return new Season();
    }
    boolean saveIt();
    String getName();
    void setName(String name);
}
