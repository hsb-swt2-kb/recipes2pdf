package sample.model.comparator;

import sample.model.IRecipe;

import java.util.Comparator;

/**
 * Created by kai on 25.05.16.
 */
public class CategoryComparator implements Comparator<Recipe> {

    @Override
    public int compare(Recipe recipe1, Recipe recipe2) {
        return recipe1.getCategory().getName().compareToIgnoreCase(recipe2.getCategory().getName());
    }
}
