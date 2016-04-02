package sample.model;

import java.util.List;

/**
 * Created by czoeller on 01.04.16.
 */
public interface ICookbook {
    static ICookbook getInstance() {
        return new Cookbook();
    }
    boolean saveIt();
    boolean delete();
    String getTitle();
    void setTitle(String title);
    List<IRecipe> getRecipes();
    void addRecipe(IRecipe recipe);
    void removeRecipe(IRecipe recipe);
}