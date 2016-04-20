package sample.model.fake;

import sample.model.IIngredient;

/**
 * Created by czoeller on 16.04.16.
 */
public class Ingredient implements IIngredient {

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
    public long getID() {
        return 0;
    }

    @Override
    public boolean saveIt() {
        return false;
    }

    @Override
    public boolean delete() {
        return false;
    }

    @Override
    public int compareTo(IIngredient o) {
        return this.getName().compareTo(o.getName());
    }
}
