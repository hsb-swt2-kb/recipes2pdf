package sample.model.comparator;

import sample.model.IRecipe;

import java.util.Comparator;

/**
 * Created by kai on 07.06.16.
 */
public class NutureComparator implements Comparator<IRecipe> {

    @Override
    public int compare(IRecipe recipe1, IRecipe recipe2) {
        return recipe1.getNurture().getName().compareToIgnoreCase(recipe2.getNurture().getName());
    }
}
