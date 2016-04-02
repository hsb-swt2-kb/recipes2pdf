package sample.model;

/**
 * Created by czoeller on 01.04.16.
 */
public interface IRecipe {
    static IRecipe getInstance() {
        return new Recipe();
    }
    String getTitle();
    void setTitle(String title);
    boolean saveIt();

    void add(IRecipeIngredient recipeIngredient);
}
