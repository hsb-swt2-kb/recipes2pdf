package sample.database.dbo;

import org.junit.Before;
import org.junit.Test;
import sample.database.dao.CookbookDAO;
import sample.model.Cookbook;
import sample.model.ICookbook;
import sample.model.Recipe;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.javalite.test.jspec.JSpec.the;

/**
 * These tests are actually using the database.
 * Each test is executed in an own transaction that is rolled back after the test.
 * @see ADatabaseTest
 * Precondition: These tests require the sandbox database that contains sample data for tests.
 * Created by czoeller on 05.05.16.
 */
public class CookbookDAOTest extends ADatabaseTest {

    private CookbookDAO cookbookDAO;

    @Before
    public void setUp() {
        this.cookbookDAO = new CookbookDAO();
    }

    /**
     * Test getAll method.
     * Postcondition: there is a new recipe inserted to the database.
     */
    @Test
    public void getAll() {
        final List<Cookbook> cookbooks = cookbookDAO.getAll();
        the(cookbooks).shouldNotBeNull();
        the(cookbooks.stream().map(ICookbook::getTitle).collect(Collectors.toList())).shouldContain("First Cookbook");
    }

    /**
     * Test findById method.
     * Postcondition: there is a new recipe inserted to the database.
     */
    @Test
    public void findById() {
        Cookbook cookbook = new Cookbook();
        String title = "A cookbook with important id";
        cookbook.setTitle(title);
        new CookbookDAO().insert(cookbook);

        Cookbook byId = cookbookDAO.findById(cookbook.getID()).orElseThrow(IllegalStateException::new);
        the(byId.getTitle()).shouldEqual(title);
    }

    /**
     * Test adding of recipes to cookbook.
     * Postcondition: The recipe is added to the cookbook.
     */
    @Test
    public void addRecipesToCookbook() {
        Cookbook cookbook = new Cookbook();
        Recipe recipe = new Recipe();

        cookbook.setTitle("An Cookbook");
        recipe.setTitle("A new recipe for an cookbook");
        recipe.setText("Example test");
        cookbook.addRecipe(recipe);
        cookbookDAO.insert(cookbook);

        Optional<Cookbook> byId = cookbookDAO.findById(cookbook.getID());
        the(byId.get().getTitle()).shouldBeEqual(cookbook.getTitle());
        the(byId.get().getRecipes().get(0).getTitle()).shouldBeEqual(recipe.getTitle());
    }
}
