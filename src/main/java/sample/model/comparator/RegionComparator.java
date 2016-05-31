package sample.model.comparator;

import sample.model.IRecipe;

import java.util.Comparator;

/**
 * Created by kai on 25.05.16.
 */
public class RegionComparator implements Comparator<Recipe> {

    @Override
    public int compare(Recipe recipe1, Recipe recipe2) {
        return recipe1.getRegion().getName().compareToIgnoreCase(recipe2.getRegion().getName());
    }
}
