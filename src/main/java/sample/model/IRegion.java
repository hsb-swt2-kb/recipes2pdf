package sample.model;

import sample.model.activejdbc.Region;

/**
 * Created by czoeller on 03.04.16.
 */
public interface IRegion {
    static IRegion getInstance() {
        return new Region();
    }
    boolean saveIt();
    String getName();
    void setName(String name);
}
