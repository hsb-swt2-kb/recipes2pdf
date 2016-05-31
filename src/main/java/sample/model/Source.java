package sample.model;

/**
 * Created by czoeller on 02.05.2016.
 */
public class Source implements ISource {

    private String name;
    private Long id;

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Long getID() {
        return this.id;
    }

    @Override
    public void setID(Long id) {
        this.id = id;
    }
}
