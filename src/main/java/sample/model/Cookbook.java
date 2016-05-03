package sample.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by noex_ on 02.05.2016.
 */
public class Cookbook implements ICookbook {
    private Long id;
    private String title;
    private List<IRecipe> recipes;

    public Cookbook() {
        this.recipes = new ArrayList<>();
    }

    public void setID(Long id) {
        this.id = id;
    }

    public Long getID() {
        return this.id;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public List<IRecipe> getRecipes() {
        return this.recipes;
    }

    @Override
    public void addRecipe(IRecipe recipe) {
        this.recipes.add(recipe);
    }

    @Override
    public void removeRecipe(IRecipe recipe) {
        this.recipes.remove(recipe);
    }
}
