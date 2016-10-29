package sample.model.comparator;

import sample.model.Recipe;

import java.util.Comparator;

/**
 * Created by kai on 28.05.16.
 */
public class RecipeTitleComparator implements Comparator<Recipe> {

    @Override
    public int compare(Recipe recipe1, Recipe recipe2) {
        return recipe1.getTitle().compareToIgnoreCase(recipe2.getTitle());
    }
}
