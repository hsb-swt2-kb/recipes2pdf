package sample.model.activejdbc;

import org.junit.Before;
import org.junit.Test;
import sample.model.Category;
import sample.model.CategoryDAO;
import sample.model.Recipe;
import sample.model.RecipeDAO;

import java.util.Optional;

import static org.javalite.test.jspec.JSpec.the;

/**
 * Created by noex_ on 30.04.2016.
 */
public class RecipeDAOTest extends ADatabaseTest {

    private RecipeDAO recipeDAO;

    @Before
    public void setUp() {
        this.recipeDAO = new RecipeDAO();
    }

    @Test
    public void insertTest() {
        final Recipe recipe = new Recipe(null);
        final int sizeBefore = recipeDAO.getAll().size();
        recipeDAO.insert(recipe);
        final int sizeAfter = recipeDAO.getAll().size();
        the(sizeAfter).shouldBeEqual(sizeBefore+1);
    }

    @Test
    public void insertAggregateTest() {
        final Recipe recipe = new Recipe(null);
        String recipeTitle = "Recipe Title";
        String categoryTitle = "Category Title";
        recipe.setTitle(recipeTitle);
        final Category category = new Category(null);
        category.setName("Test Category");
        final CategoryDAO categoryDAO = new CategoryDAO();
        categoryDAO.insert(category);
        recipeDAO.insert(recipe);

        final Optional<Recipe> first = recipeDAO.findFirst("title = ?", recipeTitle);
        the(first.get().getTitle()).shouldBeEqual(recipeTitle);
        the(first.get().getCategory().getName()).shouldBeEqual(categoryTitle);
    }
}
