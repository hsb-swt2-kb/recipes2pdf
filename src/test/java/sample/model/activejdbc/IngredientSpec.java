package sample.model.activejdbc;

import org.junit.Test;

import static org.javalite.test.jspec.JSpec.the;

/**
 * Created by czoeller on 28.03.16.
 */
public class IngredientSpec extends ADatabaseSpec {

    @Override
    @Test
    public void shouldValidateMandatoryFields(){

        IngredientDBO ingredient = new IngredientDBO();

        //check errors
        the(ingredient).shouldNotBe("valid");
        the(ingredient.errors().get("name")).shouldBeEqual("value is missing");

        //set missing values
        ingredient.set("name", "Zucker");

        //all is good:
        the(ingredient).shouldBe("valid");
    }
}
