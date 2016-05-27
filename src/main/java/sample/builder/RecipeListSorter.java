package sample.builder;

import org.apache.commons.collections.comparators.ComparatorChain;
import sample.model.IRecipe;
import sample.model.comparator.CategoryComparator;
import sample.model.comparator.RegionComparator;
import sample.model.comparator.SeasonComparator;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by kai on 25.05.16.
 */
public class RecipeListSorter {

    static List<IRecipe> sort(List<IRecipe> recipeList, List<String> sortLevelList) {
        ComparatorChain compChain = new ComparatorChain();
        Comparator comparator = null;

        for (String sortLevel : sortLevelList) {

            if (sortLevel.equalsIgnoreCase("category")) {
                comparator = new CategoryComparator();
            } else if (sortLevel.equalsIgnoreCase("region")) {
                comparator = new RegionComparator();
            } else if (sortLevel.equalsIgnoreCase("season")) {
                comparator = new SeasonComparator();
            } else {
                throw new IllegalArgumentException("Illegal Sortlevel \"" + sortLevel + "\"");
            }
            compChain.addComparator(comparator);
        }

        Collections.sort(recipeList, compChain);
        return recipeList;
    }
}
