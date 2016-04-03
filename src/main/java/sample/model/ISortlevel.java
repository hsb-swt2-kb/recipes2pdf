package sample.model;

import sample.model.activejdbc.Sortlevel;

/**
 * Created by czoeller on 03.04.16.
 */
public interface ISortlevel {
    static ISortlevel getInstance() {
        return new Sortlevel();
    }
    boolean saveIt();
    String getName();
    void setName(String name);
}
