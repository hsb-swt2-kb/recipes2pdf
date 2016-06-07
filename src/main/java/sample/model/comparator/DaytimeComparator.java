package sample.model.comparator;

import sample.model.IRecipe;

import java.util.Comparator;

/**
 * Created by kai on 07.06.16.
 */
public class DaytimeComparator implements Comparator<IRecipe> {

    @Override
    public int compare(IRecipe recipe1, IRecipe recipe2) {
        return recipe1.getDaytime().getName().compareToIgnoreCase(recipe2.getDaytime().getName());
    }
}
