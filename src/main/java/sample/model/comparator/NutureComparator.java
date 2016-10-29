package sample.model.comparator;

import sample.model.Recipe;

import java.util.Comparator;

/**
 * Created by kai on 07.06.16.
 */
public class NutureComparator implements Comparator<Recipe> {

    @Override
    public int compare(Recipe recipe1, Recipe recipe2) {
        return recipe1.getNurture().getName().compareToIgnoreCase(recipe2.getNurture().getName());
    }
}
