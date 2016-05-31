package sample.ui;

import org.junit.Before;
import org.junit.Test;
import sample.database.dao.CookbookDAO;
import sample.database.dao.RecipeDAO;
import sample.model.Cookbook;
import sample.model.Recipe;
import static org.javalite.test.jspec.JSpec.the;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * implemented by on 24.05.16 by markus
 */
public class UITest {
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void addRecipePositive() throws Exception {
        // working dir = src root dir
        boolean success=false;
        File file = new File("src/test/resources/sample/Rezepte/Bolognese.txt");
        the(UI.addRecipe(file)).shouldBeTrue();
    }

    @Test
    public void addRecipeNegative() throws Exception {
        // working dir = src root dir
        boolean success=false;
        File file = new File("src/test/resources/sample/Rezepte/keinRezept.txt");
        the(UI.addRecipe(file)).shouldBeFalse();
    }

    @Test
    public void addRecipesPositive() throws Exception {

    }

    @Test
    public void addRecipesNegative() throws Exception {

    }

    @Test
    public void getAllRecipesFromDB() throws Exception {

    }

    @Test
    public void delRecipes() throws Exception {

    }

    @Test
    public void createCookBook() throws Exception {

    }

    @Test
    public void readFile() throws Exception {

    }

}
