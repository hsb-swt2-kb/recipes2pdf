package sample.database.dbo.activejdbc;

/**
 * Created by czoeller on 26.03.16.
 */

import org.junit.Test;
import sample.database.dbo.RecipeDBO;

import static org.javalite.test.jspec.JSpec.the;

public class RecipeSpec extends ADatabaseSpec {

    @Test
    public void shouldValidateMandatoryFields(){

        RecipeDBO recipe = new RecipeDBO();

        //check errors
        the(recipe).shouldNotBe("valid");
        the(recipe.errors().get("title")).shouldBeEqual("value is missing");

        //set missing values
        recipe.set("title", "A RecipeDBO");

        //all is good:
        the(recipe).shouldBe("valid");
    }

}
