package sample.ui;

import org.junit.Test;
import sample.database.dao.CookbookDAO;
import sample.database.dao.RecipeDAO;
import sample.database.dbo.ADatabaseTest;
import sample.model.Cookbook;
import sample.model.Recipe;

import static org.javalite.test.jspec.JSpec.the;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * implemented by on 24.05.16 by markus
 */
public class UITest extends ADatabaseTest{
    @Test
    public void addRecipePositive() throws Exception {
        File file = new File("src/test/resources/sample/Rezepte/Bolognese.txt");
        the(UI.addRecipe(file)).shouldBeTrue();
    }

    @Test
    public void addRecipeNegative() throws Exception {
        // working dir = src root dir
        File file = new File("src/test/resources/sample/Rezepte/keinRezept.txt");
        the(UI.addRecipe(file)).shouldBeFalse();
    }

    @Test
    public void addRecipesPositive() throws Exception {
        List<File> files = new ArrayList<>();
        files.add(new File("src/test/resources/sample/Rezepte/Bolognese.txt"));
        files.add(new File("src/test/resources/sample/Rezepte/ChurryChekoch.html"));
        files.add(new File("src/test/resources/sample/Rezepte/Asia-Wokgemüse.html"));
        the(UI.addRecipes(files)).shouldBeTrue();
    }

    @Test
    public void addRecipesNegative() throws Exception {
        List<File> files = new ArrayList<>();
        files.add(new File("src/test/resources/sample/Rezepte/ChurryChekoch.html"));
        files.add(new File("src/test/resources/sample/Rezepte/keinRezept.html"));
        the(UI.addRecipes(files)).shouldBeFalse();
    }

    @Test
    public void delRecipes() throws Exception {
        this.addRecipePositive();
        the(new RecipeDAO().delete(new RecipeDAO().getAll().get(0))).shouldBeTrue();
    }

    @Test
    public void createCookBook() throws Exception {
        Cookbook cookbook = new Cookbook();
        cookbook.setTitle("Testkochbuch");
        the(new CookbookDAO().insert(cookbook)).shouldBeTrue();
    }
    @Test
    public void updateRecipe() throws Exception {
        List<Recipe> recipes = new RecipeDAO().getAll();
        Recipe recipe = recipes.get(0);
        recipe.setTitle("Testtiteländerung");
        the(UI.updateRecipe(recipe)).shouldBeTrue();
    }

    @Test
    public void getAllRecipesFromDB() throws Exception {
        int l1 = UI.getAllRecipesFromDB().size();
        int l2 = new RecipeDAO().getAll().size();
        the(l1).shouldBeEqual(l2);
    }

    @Test
    public void getAllCookbooksFromDB() throws Exception {
        the(UI.getAllCookbooksFromDB()).shouldBeEqual(new CookbookDAO().getAll().size());
    }

    @Test
    public void castIRecipeToRecipe() throws Exception {
    }

    @Test
    public void castICookBookToCookBook() throws Exception {
    }

    @Test
    public void addCookBook() throws Exception {
        Cookbook cookbook = new Cookbook();
        cookbook.setTitle("asdf");
        the(UI.addCookBook(cookbook)).shouldBeTrue();
    }

    @Test
    public void delCookBook() throws Exception {
        this.addCookBook();
        the(new RecipeDAO().delete(new RecipeDAO().getAll().get(0))).shouldBeTrue();
    }

    @Test
    public void changeCookBook() throws Exception {
        this.addCookBook();
        Cookbook cookbook = new CookbookDAO().getAll().get(0);
        cookbook.setTitle("blubb");
        the(UI.changeCookBook(cookbook)).shouldBeTrue();
    }

    @Test
    public void changeRecipe() throws Exception {
        this.addRecipePositive();
        Recipe recipe = new RecipeDAO().getAll().get(0);
        recipe.setTitle("blubb");
        the(UI.changeRecipe(recipe)).shouldBeTrue();
    }

    @Test
    public void delRecipe() throws Exception {
        this.addCookBook();
        Recipe recipe = new RecipeDAO().getAll().get(0);
        the(UI.delRecipe(recipe)).shouldBeTrue();
    }

    @Test
    public void searchCookBook() throws Exception {
        Cookbook cookbook = new CookbookDAO().getAll().get(0);
        the(UI.searchCookBook(cookbook.getTitle())).shouldBeTrue();
    }

    @Test
    public void searchRecipe() throws Exception {
        Recipe recipe = new RecipeDAO().getAll().get(0);
        the(UI.searchRecipe(recipe.getTitle()).getTitle().equals(recipe.getTitle())).shouldBeTrue();
    }
}
