package sample.model;

/**
 * Created by czoeller on 26.03.16.
 */

import org.junit.Test;

import static org.javalite.test.jspec.JSpec.the;

public class CookbookSpec extends ADatabaseSpec {

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

}
