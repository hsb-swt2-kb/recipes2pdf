package sample.model.dummy;

import sample.model.ICookbook;
import sample.model.IRecipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kai on 08.04.16.
 */
public class DummyCookbook implements ICookbook {

    private String title;
    private List<IRecipe> recipeList;

    @Override
    public boolean saveIt() {
        return true;
    }

    @Override
    public boolean delete() {
        return true;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public List<IRecipe> getRecipes() {

        recipeList = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            IRecipe tempRecipe = IRecipe.getInstance();
            tempRecipe.setTitle(i + ". Rezept");
            recipeList.add(tempRecipe);
        }
        return recipeList;
    }

    @Override
    public void addRecipe(IRecipe recipe) {
        recipeList.add(recipe);
    }

    @Override
    public void removeRecipe(IRecipe recipe) {
        recipeList.remove(recipe);
    }
}
