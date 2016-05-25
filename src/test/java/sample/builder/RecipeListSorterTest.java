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
        List<IRecipe> unsortedRecipeList = new ArrayList();
        List<IRecipe> sortedRecipeList = new ArrayList();
        Category vorspeise = new Category();
        Category hauptspeise = new Category();
        Category grundrezept = new Category();
        Category nachspeise = new Category();

        Region china = new Region();
        Region mexiko = new Region();
        Region grichenland = new Region();
        Region frankreich = new Region();

        IRecipe r1 = new Recipe();
        IRecipe r2 = new Recipe();
        IRecipe r3 = new Recipe();
        IRecipe r4 = new Recipe();

        Season fruehling = new Season();
        Season sommer = new Season();
        Season herbst = new Season();
        Season winter = new Season();


        fruehling.setName("Fruehling");
        sommer.setName("Sommer");
        herbst.setName("Herbst");
        winter.setName("Winter");

        vorspeise.setName("Vorspeise");
        hauptspeise.setName("Hauptspeise");
        grundrezept.setName("Grundrezept");
        nachspeise.setName("Nachspeise");

        china.setName("China");
        mexiko.setName("Mexiko");
        grichenland.setName("Grichenland");
        frankreich.setName("Frankreich");


        r1.setCategory(vorspeise);
        r2.setCategory(nachspeise);
        r3.setCategory(hauptspeise);
        r4.setCategory(vorspeise);

        r1.setSeason(fruehling);
        r2.setSeason(sommer);
        r3.setSeason(fruehling);
        r4.setSeason(winter);

        r1.setRegion(grichenland);
        r2.setRegion(mexiko);
        r3.setRegion(china);
        r4.setRegion(mexiko);


        unsortedRecipeList.add(r1);
        unsortedRecipeList.add(r2);
        unsortedRecipeList.add(r3);
        unsortedRecipeList.add(r4);

        //gewuenschtes Ergebnis bei sortierung category.region.season
        sortedRecipeList.add(r3);
        sortedRecipeList.add(r2);
        sortedRecipeList.add(r1);
        sortedRecipeList.add(r4);

        List<String> sortLevelList = new ArrayList();
        sortLevelList.add("category");
        sortLevelList.add("region");
        sortLevelList.add("season");

        rSorter.sort(unsortedRecipeList, sortLevelList);
        assertEquals(unsortedRecipeList,sortedRecipeList);

        //gewuenschtes Ergebnis bei sortierung region.category.season
        sortedRecipeList.clear();
        sortedRecipeList.add(r3);
        sortedRecipeList.add(r1);
        sortedRecipeList.add(r2);
        sortedRecipeList.add(r4);

        sortLevelList.clear();
        sortLevelList.add("region");
        sortLevelList.add("category");
        sortLevelList.add("season");
        rSorter.sort(unsortedRecipeList, sortLevelList);
        assertEquals(unsortedRecipeList,sortedRecipeList);
    }
}
