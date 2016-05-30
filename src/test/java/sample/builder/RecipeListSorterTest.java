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

    @Test
    public void testSort(){

        RecipeListSorter rSorter = new RecipeListSorter();
        List<IRecipe> recipeListToSort = new ArrayList();
        List<IRecipe> sortedRecipeList = new ArrayList();
        List<ISortlevel> sortlevel = new ArrayList<>();

        IRecipe r1 = generateRecipe("Vorspeise","Griechenland","Frühling");
        IRecipe r2 = generateRecipe("Nachspeise","Mexiko","Sommer");
        IRecipe r3 = generateRecipe("Hauptspeise","China","Frühling");
        IRecipe r4 = generateRecipe("Vorspeise","Mexiko","Winter");

        recipeListToSort.add(r1);
        recipeListToSort.add(r2);
        recipeListToSort.add(r3);
        recipeListToSort.add(r4);

        //Expected result with category.region.season
        sortedRecipeList.add(r3);
        sortedRecipeList.add(r2);
        sortedRecipeList.add(r1);
        sortedRecipeList.add(r4);

        sortlevel.add(generateSortlevel("category"));
        sortlevel.add(generateSortlevel("region"));
        sortlevel.add(generateSortlevel("season"));

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
        sortlevel.add(generateSortlevel("category"));
        sortlevel.add(generateSortlevel("season"));
        rSorter.sort(recipeListToSort, sortlevel);
        assertEquals(recipeListToSort,sortedRecipeList);
    }

    ISortlevel generateSortlevel(String sortlevelName){
        ISortlevel sortlevel = new Sortlevel();
        sortlevel.setName(sortlevelName);
        return sortlevel;
    }

    IRecipe generateRecipe(String category, String region,String season)
    {
        IRecipe recipe = new Recipe();
        ICategory testCategory = new Category();
        IRegion testRegion = new Region();
        ISeason testSeason = new Season();

        testCategory.setName(category);
        testRegion.setName(region);
        testSeason.setName(season);

        recipe.setCategory(testCategory);
        recipe.setRegion(testRegion);
        recipe.setSeason(testSeason);
        return recipe;
    }
}
