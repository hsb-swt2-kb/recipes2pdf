package sample.builder;

import org.junit.Test;
import sample.model.*;
import sample.model.comparator.CategoryComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by kai on 25.05.16.
 */
public class RecipeListSorterTest {



    /**
     * Asserts, if the sorting of Recipes does work correctly
     * Precondition: recipeListToSort is unsorted
     * Postcondition: recipeListToSort is the same as the manually sortedRecipeList.
     */
    @Test
    public void testSort(){

        RecipeListSorter rSorter = new RecipeListSorter();
        List<Recipe> recipeListToSort = new ArrayList();
        List<Recipe> sortedRecipeList = new ArrayList();
        List<Sortlevel> sortlevel = new ArrayList<>();

        Recipe r1 = generateRecipe("Vorspeise","Griechenland","Frühling");
        Recipe r2 = generateRecipe("Nachspeise","Mexiko","Sommer");
        Recipe r3 = generateRecipe("Hauptspeise","China","Frühling");
        Recipe r4 = generateRecipe("Vorspeise","Mexiko","Winter");

        recipeListToSort.add(r1);
        recipeListToSort.add(r2);
        recipeListToSort.add(r3);
        recipeListToSort.add(r4);

        //Expected result with category.region.season
        sortedRecipeList.add(r3);
        sortedRecipeList.add(r2);
        sortedRecipeList.add(r1);
        sortedRecipeList.add(r4);

        sortlevel.add(generateSortlevel("kategorie"));
        sortlevel.add(generateSortlevel("region"));
        sortlevel.add(generateSortlevel("saison"));

        rSorter.sort(recipeListToSort, sortlevel);
        assertEquals(recipeListToSort,sortedRecipeList);

        //gewuenschtes Ergebnis bei sortierung region.category.season
        sortedRecipeList.clear();
        sortedRecipeList.add(r3);
        sortedRecipeList.add(r1);
        sortedRecipeList.add(r2);
        sortedRecipeList.add(r4);

        sortlevel.clear();
        sortlevel.add(generateSortlevel("region"));
        sortlevel.add(generateSortlevel("kategorie"));
        sortlevel.add(generateSortlevel("saison"));
        rSorter.sort(recipeListToSort, sortlevel);
        assertEquals(recipeListToSort,sortedRecipeList);
    }

    Sortlevel generateSortlevel(String sortlevelName){
        Sortlevel sortlevel = new Sortlevel();
        sortlevel.setName(sortlevelName);
        return sortlevel;
    }

    Recipe generateRecipe(String category, String region,String season)
    {
        Recipe recipe = new Recipe();
        Category testCategory = new Category();
        Region testRegion = new Region();
        Season testSeason = new Season();

        testCategory.setName(category);
        testRegion.setName(region);
        testSeason.setName(season);

        recipe.setCategory(testCategory);
        recipe.setRegion(testRegion);
        recipe.setSeason(testSeason);
        return recipe;
    }
}
