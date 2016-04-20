package sample.model.fake;

import sample.model.*;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by czoeller on 18.04.16.
 */
public class Recipe implements IRecipe {

    private Long id = new Long(0);
    private String title;
    private Map<IIngredient, Map<Integer, IUnit>> ingredients = new TreeMap<>();

    @Override
    public boolean saveIt() {
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
    public void add(String ingredientName, int amount, String unitName) {
        // Violation of IoC for special case of Builder
        IIngredient ingredient = new Ingredient();
        Map<Integer, IUnit> ingredientDetails = new TreeMap<>();

        ingredient.setName(ingredientName);

        IUnit unit = IUnit.getInstance();
        unit.setName(unitName);

        ingredientDetails.put(amount, unit);
        this.ingredients.put(ingredient, ingredientDetails);
    }

    @Override
    public Long getID() {
        return id;
    }


    @Override
    public Map<IIngredient, Map<Integer, IUnit>> getIngredients() {
        return ingredients;
    }

    @Override
    public String getCategory() {
        return "Kategorie";
    }

    @Override
    public int getCategoryNumber() {
        return 1;
    }

    @Override
    public String getRecipeText() {
        String text = "";
        for (int i = 1; i <= 20; i++) {
            text += "Texstzeile " + i + "\\\\";
        }
        return text;
    }
}

