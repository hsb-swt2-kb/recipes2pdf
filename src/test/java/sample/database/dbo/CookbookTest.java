package sample.database.dbo;

import org.junit.Test;
import sample.database.dao.CookbookDAO;
import sample.database.dao.SortlevelDAO;
import sample.model.Cookbook;
import sample.model.ISortlevel;
import sample.model.Sortlevel;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.javalite.test.jspec.JSpec.the;

/**
 * These tests are actually using the database.
 * Each test is executed in an own transaction that is rolled back after the test.
 * @see ADatabaseTest
 * Precondition: These tests require the sandbox database that contains sample data for tests.
 * Created by czoeller on 25.05.16.
 */
public class CookbookTest extends ADatabaseTest {

    /**
     * Test creation of sortlevels of cookbook.
     * Postcondition: Sortlevels are created and associated to cookbook.
     */
    @Test
    public void testSortlevel() {
        CookbookDAO cookbookDAO = new CookbookDAO();
        Cookbook cookbook = new Cookbook();
        cookbook.setTitle("Test Cookbook for Sortlevel");
        cookbookDAO.insert(cookbook);
        cookbook.setTitle("Test Title");
        Sortlevel sortlevel1 = new Sortlevel();
        Sortlevel sortlevel2 = new Sortlevel();
        String sortlevel1_name = "Category";
        String sortlevel2_name = "Season";
        sortlevel1.setName(sortlevel1_name);
        sortlevel2.setName(sortlevel2_name);
        new SortlevelDAO().insert(sortlevel1);
        new SortlevelDAO().insert(sortlevel2);
        cookbook.addSortlevel(sortlevel1);
        cookbook.addSortlevel(sortlevel2);
        cookbookDAO.update(cookbook);
        Optional<Cookbook> byId = new CookbookDAO().findById(cookbook.getID());
        byId.orElseThrow(IllegalStateException::new);
        final List<String> sortlevelNames = extractSortlevelNames(byId.get());

        the(sortlevelNames).shouldContain(sortlevel1_name);
        the(sortlevelNames).shouldContain(sortlevel2_name);
    }

    /**
     * Test creation of sortlevels of cookbook.
     * Postcondition: Sortlevels are created and associated to cookbook.
     */
    @Test
    public void testSortlevelImplicitAggregate() {
        CookbookDAO cookbookDAO = new CookbookDAO();
        Cookbook cookbook = new Cookbook();
        cookbook.setTitle("Test Cookbook for Sortlevel");
        cookbookDAO.insert(cookbook);
        cookbook.setTitle("Test Title");
        Sortlevel sortlevel1 = new Sortlevel();
        Sortlevel sortlevel2 = new Sortlevel();
        String sortlevel1_name = "Category";
        String sortlevel2_name = "Season";
        sortlevel1.setName(sortlevel1_name);
        sortlevel2.setName(sortlevel2_name);
        cookbook.addSortlevel(sortlevel1);
        cookbook.addSortlevel(sortlevel2);
        cookbookDAO.update(cookbook);
        Optional<Cookbook> byId = new CookbookDAO().findById(cookbook.getID());
        byId.orElseThrow(IllegalStateException::new);
        final List<String> sortlevelNames = extractSortlevelNames(byId.get());

        the(sortlevelNames).shouldContain(sortlevel1_name);
        the(sortlevelNames).shouldContain(sortlevel2_name);
    }

    /**
     * Test creation of aleady persisted sortlevels of cookbook.
     * Postcondition: Sortlevels associated to cookbook.
     */
    @Test
    public void testWithAlreadyPersistedSortlevel() {
        CookbookDAO cookbookDAO = new CookbookDAO();
        Cookbook cookbook = new Cookbook();
        cookbook.setTitle("Test Cookbook for Sortlevel");
        cookbookDAO.insert(cookbook);
        cookbook.setTitle("Test Title");
        Sortlevel sortlevel1 = new Sortlevel();
        String sortlevel_name = "Saison";
        sortlevel1.setName(sortlevel_name);
        cookbook.addSortlevel(sortlevel1);
        cookbookDAO.update(cookbook);
        Optional<Cookbook> byId = new CookbookDAO().findById(cookbook.getID());
        byId.orElseThrow(IllegalStateException::new);
        final List<String> sortlevelNames = extractSortlevelNames(byId.get());

        the( sortlevelNames ).shouldContain(sortlevel_name);
    }

    /**
     * Get the sortlevel names of cookbook.
     * @param cookbook The cookbook to collect from.
     * @return the list of sortlevel names
     */
    private List<String> extractSortlevelNames(Cookbook cookbook) {
        return cookbook.getSortlevel()
            .stream()
            .map(ISortlevel::getName)
            .collect(Collectors.toList()
        );
    }
}
