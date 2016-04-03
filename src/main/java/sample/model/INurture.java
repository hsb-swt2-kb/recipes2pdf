package sample.model;

import sample.model.activejdbc.Nurture;

/**
 * Created by czoeller on 03.04.16.
 */
public interface INurture {
    static INurture getInstance() {
        return new Nurture();
    }
    boolean saveIt();
    String getName();
    void setName(String name);
}
