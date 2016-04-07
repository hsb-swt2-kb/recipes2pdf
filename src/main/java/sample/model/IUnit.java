package sample.model;

import sample.model.dummy.DummyUnit;

/**
 * Created by kai on 07.04.16.
 */
public interface IUnit {
    static IUnit getInstance() {
        return new DummyUnit();
    }

    boolean saveIt();

    String getName();

    void setName(String name);
}
