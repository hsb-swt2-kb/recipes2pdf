package sample.model;

/**
 * Created by czoeller on 02.04.16.
 */
public interface IIngredient {
    static IIngredient getInstance() { return new Ingredient(); }

    void setName(String name);

    boolean saveIt();
    boolean delete();

    void add(IRecipeIngredient recipeIngredient);
}
