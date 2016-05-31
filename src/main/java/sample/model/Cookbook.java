package sample.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by czoeller on 02.05.2016.
 */
public class Cookbook implements ICookbook {
    private Long id;
    private String title;
    private List<IRecipe> recipes;
    private List<ISortlevel> sortlevel;

    public Cookbook() {
        this.recipes = new ArrayList<>();
        this.sortlevel = new ArrayList<>();
    }

    public Long getID() {
        return this.id;
    }

    public void setID(Long id) {
        this.id = id;
    }

    public void setRecipes(List<IRecipe> recipes){
        this.recipes = recipes;
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
    public List<ISortlevel> getSortlevel() {
        return this.sortlevel;
    }

    @Override
    public void addSortlevel(ISortlevel sortlevel) {
        this.sortlevel.add(sortlevel);
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
