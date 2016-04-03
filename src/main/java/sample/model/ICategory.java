package sample.model;

import sample.model.activejdbc.Category;

/**
 * Created by czoeller on 03.04.16.
 */
public interface ICategory {
    static ICategory getInstance() {
        return new Category();
    }
    boolean saveIt();
    String getName();
    void setName(String name);
}
