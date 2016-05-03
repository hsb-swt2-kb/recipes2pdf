package sample.model;

/**
 * Created by czoeller on 03.05.16.
 */
public class Region implements IRegion {
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
