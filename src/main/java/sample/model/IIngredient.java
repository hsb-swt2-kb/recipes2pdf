package sample.model;

import sample.model.dummy.DummyIngredient;

/**
 * Created by kai on 07.04.16.
 */
public interface IIngredient {
    static IIngredient getInstance() {
        return new DummyIngredient();
    }

    String getName();

    void setName(String name);

    boolean saveIt();

    boolean delete();
}
