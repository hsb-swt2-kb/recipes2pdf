package sample.model.activejdbc;

/**
 * Created by czoeller on 26.03.16.
 */

import org.junit.Test;

import static org.javalite.test.jspec.JSpec.the;

public class RecipeSpec extends ADatabaseSpec {

    @Test
    public void shouldValidateMandatoryFields(){

        Recipe recipe = new Recipe();

        //check errors
        the(recipe).shouldNotBe("valid");
        the(recipe.errors().get("title")).shouldBeEqual("value is missing");

        //set missing values
        recipe.set("title", "A Recipe");

        //all is good:
        the(recipe).shouldBe("valid");
    }

}
