package sample.parser;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import sample.external.ChefkochAPI;
import sample.model.IRecipe;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static org.javalite.test.jspec.JSpec.the;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by czoeller on 16.04.16.
 */
@RunWith(MockitoJUnitRunner.class)
public class ChefkochParserTest {

    private final String EXAMPLE_RECIPE_ID = "1616691268862802";

    @Mock
    private IRecipe fakeRecipe = IRecipe.getInstance();
    @Mock
    private ChefkochAPI mockChefkochAPI;
    private ChefkochParser chefkochParser;

    private String exampleRecipeRaw;
    private IRecipe exampleRecipe;

    @Before
    public void setUp() throws Throwable {
        when(mockChefkochAPI.search(any(String.class))).thenReturn(Optional.ofNullable(EXAMPLE_RECIPE_ID));
        when(mockChefkochAPI.findById(any(String.class))).thenReturn(Optional.ofNullable(fakeRecipe));

        this.chefkochParser = new ChefkochParser();
        chefkochParser.setChefkochAPI(mockChefkochAPI);
        chefkochParser.setRecipe(fakeRecipe);

        final Path path = Paths.get(getClass().getClassLoader().getResource("sample/parser/ChefkochRecipe.html").getPath());
        this.exampleRecipeRaw = new String(Files.readAllBytes(path));
        this.exampleRecipe = chefkochParser.parse(exampleRecipeRaw);
    }

    @Test
    public void theParserShouldAcceptTheRecipe() {
        the(chefkochParser.accepts(exampleRecipeRaw)).shouldBeTrue();
    }

    @Test
    public void theParserShouldNotAcceptUnknownRecipeFormat() {
        the(chefkochParser.accepts("unknown recipe format")).shouldBeFalse();
    }

    @Test
    public void theParserShouldReturnARecipe() throws Throwable {
        the(exampleRecipe).shouldBeA(IRecipe.class);
    }

    @Test
    public void chefkochAPIcalledWithProperID() {
        verify(mockChefkochAPI, times(1)).findById(EXAMPLE_RECIPE_ID);
    }


}
