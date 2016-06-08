package sample.model.comparator;

import sample.model.IRecipe;

import java.util.Comparator;

/**
 * Created by kai on 07.06.16.
 */
public class SourceComparator implements Comparator<IRecipe> {

    @Override
    public int compare(IRecipe recipe1, IRecipe recipe2) {
        return recipe1.getSource().getName().compareToIgnoreCase(recipe2.getSource().getName());
    }
}
