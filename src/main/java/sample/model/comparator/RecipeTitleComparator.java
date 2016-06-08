package sample.model.comparator;

import sample.model.IRecipe;

import java.util.Comparator;

/**
 * Created by kai on 28.05.16.
 */
public class RecipeTitleComparator implements Comparator<IRecipe> {

    @Override
    public int compare(IRecipe recipe1, IRecipe recipe2) {
        return recipe1.getTitle().compareToIgnoreCase(recipe2.getTitle());
    }
}
