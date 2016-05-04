package sample.model;

import sample.model.IIdentifiable;
import sample.model.IUnit;

/**
 * Created by czoeller on 28.04.16.
 */
public class Unit implements IUnit, IIdentifiable {
    private String name;
    private Long id;

    @Override
    public Long getID() {
        return this.id;
    }

    @Override
    public void setID(Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
