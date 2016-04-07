package sample.model.dummy;

import sample.model.IUnit;

/**
 * Created by kai on 07.04.16.
 */
public class DummyUnit implements IUnit {
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
