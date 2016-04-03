package sample.model;

import sample.model.activejdbc.Course;

/**
 * Created by czoeller on 03.04.16.
 */
public interface ICourse {
    static ICourse getInstance() {
        return new Course();
    }
    boolean saveIt();
    String getName();
    void setName(String name);
}
