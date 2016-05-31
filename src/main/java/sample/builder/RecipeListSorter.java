package sample.builder;

import org.apache.commons.collections.comparators.ComparatorChain;
import sample.model.IRecipe;
import sample.model.ISortlevel;
import sample.model.comparator.CategoryComparator;
import sample.model.comparator.RecipeComparator;
import sample.model.comparator.RegionComparator;
import sample.model.comparator.SeasonComparator;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * This Class is for Sorting a recipelist according to a List of ISortlevels, where the first list element is the
 * primary sortlevel, the second listelement is the secondary sortlevel and so on
 */
public class RecipeListSorter {

    static List<IRecipe> sort(List<IRecipe> recipeList, List<ISortlevel> sortLevelList) {
        ComparatorChain compChain = new ComparatorChain();
        Comparator comparator = null;

        for (int i=0; i<sortLevelList.size();i++) {

            if (sortLevelList.get(i).getName().equalsIgnoreCase("category")) {
                comparator = new CategoryComparator();
            } else if (sortLevelList.get(i).getName().equalsIgnoreCase("region")) {
                comparator = new RegionComparator();
            } else if (sortLevelList.get(i).getName().equalsIgnoreCase("season")) {
                comparator = new SeasonComparator();
            } else {
                throw new IllegalArgumentException("Illegal Sortlevel \"" + sortLevelList.get(i) + "\"");
            }
            compChain.addComparator(comparator);
        }
        if (compChain.size() == 0){
            compChain.addComparator(new RecipeComparator());
        }
        Collections.sort(recipeList, compChain);
        return recipeList;
    }
}
