package sample.parser;

import org.junit.Before;
import org.junit.Test;
import sample.exceptions.CouldNotParseException;
import sample.model.IRecipe;
import sample.model.Recipe;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static org.javalite.test.jspec.JSpec.the;

/**
 * implemented by on 31.05.16 by markus
 */
public class ParserTest {
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testParsePositive() throws IOException, CouldNotParseException {
        File file = new File("src/test/resources/sample/Rezepte/Bolognese.txt");
        IRecipe recipe = Parser.parse(file);
        the(recipe.getTitle()).shouldBeEqual("Bolognese");
        the(recipe.getIngredients().size()).shouldBeEqual(15);
    }

    @Test(expected = CouldNotParseException.class)
    public void testParseCouldNotParse() throws IOException, CouldNotParseException {
        File file = new File("src/test/resources/sample/Rezepte/FalscheBolognese.txt");
        Parser.parse(file);
    }

    @Test(expected = FileNotFoundException.class)
    public void testParseFileNotFound()throws IOException, CouldNotParseException {
        File file = new File("src/test/resources/sample/Rezepte/fehlendeDatei.txt");
        Parser.parse(file);
    }

    @Test
    public void readFile() throws IOException, CouldNotParseException {
        ArrayList<String> lines = Parser.readFile("src/test/resources/sample/Rezepte/textdatei.txt");
        the(lines).shouldContain("asdf");
        the(lines).shouldContain("blubb fubar");
    }
}
