package sample.model;

import sample.model.activejdbc.Unit;

/**
 * Created by czoeller on 16.04.16.
 */
public interface IUnit {
    static IUnit getInstance() {
        return new Unit();
    }

    boolean saveIt();

    String getName();

    void setName(String name);
}
