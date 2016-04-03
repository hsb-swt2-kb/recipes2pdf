package sample.model;

import sample.model.activejdbc.Rating;

/**
 * Created by czoeller on 03.04.16.
 */
public interface IRating {
    static IRating getInstance() {
        return new Rating();
    }
    boolean saveIt();
    String getName();
    void setName(String name);
}
