package sample.model.activejdbc;

import sample.model.IUnit;

/**
 * Created by czoeller on 16.04.16.
 */
public class Unit implements IUnit {

    private String name;

    @Override
    public boolean saveIt() {
        return true;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
