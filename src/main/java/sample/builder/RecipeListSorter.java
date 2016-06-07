package sample.builder;

import org.apache.commons.collections4.comparators.ComparatorChain;
import sample.model.IRecipe;
import sample.model.ISortlevel;
import sample.model.comparator.*;

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

        for (int i = 0; i < sortLevelList.size(); i++) {
            final String sortlevelName = sortLevelList.get(i).getName();
            if (sortlevelName.equalsIgnoreCase("Kategorie")) {
                comparator = new CategoryComparator();
            } else if (sortlevelName.equalsIgnoreCase("Region")) {
                comparator = new RegionComparator();
            } else if (sortlevelName.equalsIgnoreCase("Saison")) {
                comparator = new SaisonComparator();
            } else if (sortlevelName.equalsIgnoreCase("Gerichtart")) {
                comparator = new CourseComparator();
            } else if (sortlevelName.equalsIgnoreCase("Ernährungsart")) {
                comparator = new NutureComparator();
            } else if (sortlevelName.equalsIgnoreCase("Tageszeit")) {
                comparator = new DaytimeComparator();
            } else if (sortlevelName.equalsIgnoreCase("Rezeptquelle")) {
                comparator = new SourceComparator();
            } else {
                throw new IllegalArgumentException("Illegal Sortlevel \"" + sortLevelList.get(i) + "\"");
            }
            compChain.addComparator(comparator);
        }
        if (compChain.size() == 0) {
            compChain.addComparator(new RecipeTitleComparator());
        }
        Collections.sort(recipeList, compChain);
        return recipeList;
    }
}
