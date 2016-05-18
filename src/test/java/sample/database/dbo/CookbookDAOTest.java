package sample.database.dbo;

import org.junit.Before;
import org.junit.Test;
import sample.database.dao.CookbookDAO;
import sample.model.Cookbook;
import sample.model.Recipe;

import java.util.List;
import java.util.Optional;

import static org.javalite.test.jspec.JSpec.the;

/**
 * Created by czoeller on 05.05.16.
 */
public class CookbookDAOTest extends ADatabaseTest {


    private CookbookDAO cookbookDAO;

    @Before
    public void setUp() {
        this.cookbookDAO = new CookbookDAO();
    }

    @Test
    public void getAll() {
        final List<Cookbook> cookbooks = cookbookDAO.getAll();
        the(cookbooks).shouldNotBeNull();
        the(cookbooks.get(0).getTitle()).shouldEqual("First Cookbook");
    }

    @Test
    public void findById() {
        final Optional<Cookbook> cookbook = cookbookDAO.findById(1L);
        cookbook.orElseThrow(IllegalStateException::new);
        the(cookbook.get().getTitle()).shouldEqual("First Cookbook");
    }

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
