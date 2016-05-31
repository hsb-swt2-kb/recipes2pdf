package sample.builder;

import org.apache.commons.collections.comparators.ComparatorChain;
import sample.model.IRecipe;
import sample.model.Recipe;
import sample.model.comparator.CategoryComparator;
import sample.model.comparator.RegionComparator;
import sample.model.comparator.SeasonComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * This Class is for Sorting a recipelist according to a List of ISortlevels, where the first list element is the
 * primary sortlevel, the second listelement is the secondary sortlevel and so on
 */
public class RecipeListSorter {

    static void sort(List<IRecipe> recipeList, String sortLevelChain)
    {
        ComparatorChain compChain = new ComparatorChain();
        Comparator comparator = null;

        List<String> sortLevelList  = Pattern.compile("\\.").splitAsStream(sortLevelChain).collect(Collectors.toList());

        for(String sortLevel:sortLevelList){

            if (sortLevel.equalsIgnoreCase("category")){
                comparator = new CategoryComparator();
            }
            else if(sortLevel.equalsIgnoreCase("region")){
                comparator = new RegionComparator();
            }
            else if(sortLevel.equalsIgnoreCase("season")){
                comparator = new SeasonComparator();
            }
            else {
                throw new IllegalArgumentException("Illegal Sortlevel \""+sortLevel+"\"");
            }
            compChain.addComparator(comparator);
        }

        Collections.sort(recipeList,compChain);
    }
}
