package sample.model.activejdbc;

import org.junit.Before;
import org.junit.Test;
import sample.model.ICookbook;

import java.util.List;
import java.util.Optional;

import static org.javalite.test.jspec.JSpec.the;

/**
 * Created by czoeller on 30.03.16.
 */
public class CookbookRepositoryTest extends ADatabaseTest {

    private CookbookRepository cookbookRepository;

    @Before
    public void setUp() throws Exception {
        this.cookbookRepository = new CookbookRepository();
    }

    @Test
    public void getCookbooks() throws Exception {
        final List<ICookbook> cookbooks = cookbookRepository.getAll();
        the(cookbooks).shouldNotBeNull();
        the(cookbooks.get(0).getTitle()).shouldEqual("A Cookbook");
    }

    @Test
    public void getCookbookById() throws Exception {
        final Optional<ICookbook> cookbook = cookbookRepository.findById(1);
        cookbook.orElseThrow(IllegalStateException::new);
        the(cookbook.get().getTitle()).shouldEqual("A Cookbook");
    }

}
