package sample.model;

/**
 * Created by czoeller on 30.04.2016.
 */
public class Category implements ICategory, Identity {
    private Long id;
    private String name;

    public void setID(Long id) {
        this.id = id;
    }

    public Long getID() {
        return this.id;
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

