package sample.model;

import sample.model.activejdbc.Source;

/**
 * Created by czoeller on 03.04.16.
 */
public interface ISource {
    static ISource getInstance() {
        return new Source();
    }
    boolean saveIt();
    String getName();
    void setName(String name);
}
