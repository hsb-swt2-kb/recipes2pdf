package sample.model;

/**
 * Created by czoeller on 26.03.16.
 */

import org.javalite.activejdbc.Association;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.javalite.test.jspec.JSpec.the;

public class CookbookSpec extends ADatabaseSpec {

    private CookbookRepository cookbookRepository;

    @Before
    public void setUp() throws Exception {
        this.cookbookRepository = new CookbookRepository();
    }

    @Override
    @Test
    public void shouldValidateMandatoryFields(){

        Cookbook cookbook = new Cookbook();

        //check errors
        the(cookbook).shouldNotBe("valid");
        the(cookbook.errors().get("title")).shouldBeEqual("value is missing");

        //set missing values
        cookbook.set("title", "A Cookbook");

        //all is good:
        the(cookbook).shouldBe("valid");
    }


    @Test
    public void addRecipe() {
        Optional<ICookbook> cookbook = cookbookRepository.getCookbookById(1);
        cookbook.orElseThrow(IllegalStateException::new);

        IRecipe recipe = new Recipe();
        recipe.setTitle("A second Recipe");
        recipe.saveIt();

        cookbook.get().addRecipe(recipe);
        final LazyList<Recipe> relatives = ((Model) cookbook.get()).getAll(Recipe.class);
        //the(relatives).shouldContain(recipe);
    }

}
