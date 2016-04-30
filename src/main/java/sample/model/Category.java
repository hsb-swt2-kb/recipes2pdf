package sample.model;

/**
 * Created by noex_ on 30.04.2016.
 */
public class Category implements ICategory {
    private Long id;
    private String name;

    public Category(Long id) {
        this.id = id;
    }

    @Override
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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
