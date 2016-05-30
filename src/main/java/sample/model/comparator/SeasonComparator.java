package sample.model.comparator;

import sample.model.IRecipe;
import sample.model.Recipe;

import java.util.Comparator;

/**
 * Created by kai on 25.05.16.
 */
public class SeasonComparator implements Comparator<IRecipe>{

    @Override
    public int compare(IRecipe recipe1, IRecipe recipe2) {
        return recipe1.getSeason().getName().compareToIgnoreCase(recipe2.getSeason().getName());
    }
}
