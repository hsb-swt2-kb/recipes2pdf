package sample.model.dummy;

import sample.model.IIngredient;

/**
 * Created by kai on 07.04.16.
 */
public class DummyIngredient implements IIngredient, Comparable<DummyIngredient> {
    private String name;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean saveIt() {
        return true;
    }

    @Override
    public boolean delete() {
        return true;
    }

    @Override
    public int compareTo(DummyIngredient o) {
        return this.name.compareTo(o.getName());
    }
}
