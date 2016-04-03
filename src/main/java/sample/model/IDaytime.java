package sample.model;

import sample.model.activejdbc.Daytime;

/**
 * Created by czoeller on 03.04.16.
 */
public interface IDaytime {
    static IDaytime getInstance() {
        return new Daytime();
    }
    boolean saveIt();
    String getName();
    void setName(String name);
}
