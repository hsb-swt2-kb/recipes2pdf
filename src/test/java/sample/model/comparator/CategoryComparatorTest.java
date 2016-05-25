package sample.model.comparator;

import org.junit.Test;
import sample.model.Category;
import sample.model.Recipe;
import sample.model.comparator.CategoryComparator;
import sample.model.comparator.RegionComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by kai on 25.05.16.
 */
public class CategoryComparatorTest {
    @Test
    public void testSort(){
        CategoryComparator rcomp = new CategoryComparator();
        List<Recipe> unsortedRecipeList = new ArrayList();
        List<Recipe> sortedRecipeList = new ArrayList();

        Category vorspeise = new Category();
        Category hauptspeise = new Category();
        Category grundrezept = new Category();
        Category nachspeise = new Category();

        Recipe vorspeiseRecipe = new Recipe();
        Recipe hauptspeiseRecipe = new Recipe();
        Recipe grundrezeptRecipe = new Recipe();
        Recipe nachspeiseRecipe = new Recipe();

        vorspeise.setName("Vorspeise");
        hauptspeise.setName("Hauptspeise");
        grundrezept.setName("Grundrezept");
        nachspeise.setName("Nachspeise");

        vorspeiseRecipe.setCategory(vorspeise);
        hauptspeiseRecipe.setCategory(hauptspeise);
        grundrezeptRecipe.setCategory(grundrezept);
        nachspeiseRecipe.setCategory(nachspeise);

        unsortedRecipeList.add(vorspeiseRecipe);
        unsortedRecipeList.add(hauptspeiseRecipe);
        unsortedRecipeList.add(grundrezeptRecipe);
        unsortedRecipeList.add(nachspeiseRecipe);

        sortedRecipeList.add(grundrezeptRecipe);
        sortedRecipeList.add(hauptspeiseRecipe);
        sortedRecipeList.add(nachspeiseRecipe);
        sortedRecipeList.add(vorspeiseRecipe);

        Collections.sort(unsortedRecipeList,rcomp);

        assertEquals(unsortedRecipeList,sortedRecipeList);

    }
}
