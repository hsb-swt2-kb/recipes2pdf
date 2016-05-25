package sample.model.comparator;

import org.junit.Test;
import sample.model.Recipe;
import sample.model.Region;
import sample.model.comparator.RegionComparator;
import sample.model.comparator.SeasonComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by kai on 25.05.16.
 */
public class RegionComparatorTest {
    @Test
    public void testSort(){
        RegionComparator rcomp = new RegionComparator();
        List<Recipe> unsortedRecipeList = new ArrayList();
        List<Recipe> sortedRecipeList = new ArrayList();

        Region china = new Region();
        Region mexiko = new Region();
        Region grichenland = new Region();
        Region frankreich = new Region();

        Recipe mexikoRecipe = new Recipe();
        Recipe chinaRecipe = new Recipe();
        Recipe frankreichRecipe = new Recipe();
        Recipe grichenlandRecipe = new Recipe();

        china.setName("China");
        mexiko.setName("Mexiko");
        grichenland.setName("Grichenland");
        frankreich.setName("Frankreich");

        mexikoRecipe.setRegion(mexiko);
        chinaRecipe.setRegion(china);
        frankreichRecipe.setRegion(frankreich);
        grichenlandRecipe.setRegion(grichenland);

        unsortedRecipeList.add(mexikoRecipe);
        unsortedRecipeList.add(grichenlandRecipe);
        unsortedRecipeList.add(frankreichRecipe);
        unsortedRecipeList.add(chinaRecipe);

        sortedRecipeList.add(chinaRecipe);
        sortedRecipeList.add(frankreichRecipe);
        sortedRecipeList.add(grichenlandRecipe);
        sortedRecipeList.add(mexikoRecipe);

        Collections.sort(unsortedRecipeList,rcomp);

        assertEquals(unsortedRecipeList,sortedRecipeList);

    }
}
