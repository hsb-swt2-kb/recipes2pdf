package sample.model;

/**
 * Created by czoeller on 26.03.16.
 */

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.javalite.test.jspec.JSpec.the;

public class CookbookSpec extends ADatabaseSpec {

    private ICookbookRepository cookbookRepository;

    @Before
    public void setUp() throws Exception {
        this.cookbookRepository = new CookbookRepository();
    }

    @Override
    @Test
    public void shouldValidateMandatoryFields() {

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
        Optional<ICookbook> cookbook = cookbookRepository.findById(1);
        cookbook.orElseThrow(IllegalStateException::new);

        IRecipe recipe = IRecipe.getInstance();
        final String uniqueTitle = "A second Recipe with $id3ntifi3r41";
        recipe.setTitle(uniqueTitle);
        recipe.saveIt();

        cookbook.get().addRecipe(recipe);
        final List<IRecipe> recipes = cookbook.get().getRecipes();
        final List<String> titles = recipes.
            stream()
            .map(d -> d.getTitle())
            .collect(Collectors.toList());

        the(titles).shouldContain(recipe.getTitle());
    }

}
