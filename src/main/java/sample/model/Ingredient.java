package sample.model;

/**
 * Created by czoeller on 28.04.16.
 */
public class Ingredient implements IIngredient {
    private String name;
    private Long id;

    public Ingredient(Long id) {
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

    @Override
    public long getID() {
        return this.id;
    }

    @Override
    public int compareTo(IIngredient o) {
        return 0;
    }
}
