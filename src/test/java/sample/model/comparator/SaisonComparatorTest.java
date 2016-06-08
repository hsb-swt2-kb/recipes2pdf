package sample.model.comparator;

import org.junit.Test;
import sample.model.Recipe;
import sample.model.Season;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
/**
 * Created by kai on 25.05.16.
 */
public class SaisonComparatorTest {
    @Test
    public void testSort(){
        SaisonComparator scomp = new SaisonComparator();
        List<Recipe> unsortedRecipeList = new ArrayList();
        List<Recipe> sortedRecipeList = new ArrayList();

        Season s1 = new Season();
        Season s2 = new Season();
        Season s3 = new Season();
        Season s4 = new Season();

        Recipe fruehlingRecipe = new Recipe();
        Recipe sommerRecipe = new Recipe();
        Recipe herbstRecipe = new Recipe();
        Recipe winterRecipe = new Recipe();

        s1.setName("Fruehling");
        s2.setName("Sommer");
        s3.setName("Herbst");
        s4.setName("Winter");

        fruehlingRecipe.setSeason(s1);
        sommerRecipe.setSeason(s2);
        herbstRecipe.setSeason(s3);
        winterRecipe.setSeason(s4);

        unsortedRecipeList.add(sommerRecipe);
        unsortedRecipeList.add(fruehlingRecipe);
        unsortedRecipeList.add(herbstRecipe);
        unsortedRecipeList.add(winterRecipe);

        sortedRecipeList.add(fruehlingRecipe);
        sortedRecipeList.add(herbstRecipe);
        sortedRecipeList.add(sommerRecipe);
        sortedRecipeList.add(winterRecipe);

        Collections.sort(unsortedRecipeList,scomp);

        assertEquals(unsortedRecipeList,sortedRecipeList);

    }
}
