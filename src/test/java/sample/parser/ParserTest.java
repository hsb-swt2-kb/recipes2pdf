package sample.parser;

import org.junit.Before;
import org.junit.Test;
import sample.exceptions.CouldNotParseException;
import sample.model.Recipe;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.javalite.test.jspec.JSpec.the;
import static org.junit.Assert.*;

/**
 * implemented by on 31.05.16 by markus
 */
public class ParserTest {
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testParsePositive() throws Exception {
        File file = new File("src/test/resources/sample/Rezepte/Bolognese.txt");
        Recipe recipe = Parser.parse(file);
        the(recipe.getTitle()).shouldBe("Bolognese");
        the(recipe.getIngredients().size()).shouldBe("15");
    }

    @Test
    public void testParseCouldNotParse() {
        boolean success=false;
        File file = new File("src/test/resources/sample/Rezepte/FalscheBolognese.txt");
        try {
            Recipe recipe = Parser.parse(file);
        }
        catch(CouldNotParseException e){
            success=true;
        }
        catch(FileNotFoundException e){}
        the(success).shouldBeTrue();
    }

    @Test
    public void testParseFileNotFound() {
        boolean success=false;
        File file = new File("src/test/resources/sample/Rezepte/fehlendeDatei.txt");
        try {
            Recipe recipe = Parser.parse(file);
        }
        catch(CouldNotParseException e){
        }
        catch(FileNotFoundException e){
            success=true;
        }
        the(success).shouldBeTrue();
    }

    @Test
    public void readFile() throws Exception {
        boolean success=false;
        File file = new File("src/test/resources/sample/Rezepte/fehlendeDatei.txt");
        ArrayList<String> lines = Parser.readFile("src/test/resources/sample/Rezepte/textdatei.txt");
        the(lines.get(0)).shouldBeEqual("asdf");
        the(lines.get(1)).shouldBeEqual("blubb fubar");
    }
}
