package sample.parser;

import org.junit.Before;
import org.junit.Test;
import sample.model.Recipe;
import sample.model.Recipe;
import sample.util.ResourceLoader;

import java.util.ArrayList;

import static org.javalite.test.jspec.JSpec.the;

/*
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import sample.external.ChefkochAPI;
import sample.model.Recipe;
import sample.util.ResourceLoader;

import java.util.Optional;

import static org.javalite.test.jspec.JSpec.the;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;*/

/**
 * Created by czoeller on 16.04.16.
 * Updated by fpfennig on 30.05.16
 */
//@RunWith(MockitoJUnitRunner.class)
public class ChefkochParserTest {

    private Recipe recipe;
    private ChefkochParser ckParser;
    private ArrayList<String> rawRecipeList = new ArrayList<>();

    @Before
    public void setUp() throws Throwable {

        this.ckParser   = new ChefkochParser();
        this.recipe     = new Recipe();

        this.rawRecipeList.add(ResourceLoader.loadFileContents(this.getClass(), "CK_test_zutaten.html"));
        this.rawRecipeList.add(ResourceLoader.loadFileContents(this.getClass(), "CK_test_1.html"));
        this.rawRecipeList.add(ResourceLoader.loadFileContents(this.getClass(), "CK_test_2.html"));
        this.rawRecipeList.add(ResourceLoader.loadFileContents(this.getClass(), "CK_test_3.html"));
        this.rawRecipeList.add(ResourceLoader.loadFileContents(this.getClass(), "CK_test_4.html"));
        this.rawRecipeList.add(ResourceLoader.loadFileContents(this.getClass(), "CK_test_5.html"));
    }

    @Test
    public void theParserShouldAcceptTheRecipe() {
        for (String rawRecipe: rawRecipeList) {
            ArrayList<String> list = new ArrayList<>();
            list.add(rawRecipe);
            the(ckParser.accepts(list)).shouldBeTrue();
        }
    }

    @Test
    public void theParserShouldNotAcceptUnknownRecipeFormat() {
        ArrayList<String> list = new ArrayList<>();
        list.add("unknown recipe format");
        the(ckParser.accepts(list)).shouldBeFalse();
    }

    @Test
    public void theParserShouldReturnARecipe() throws Throwable {
        the(recipe).shouldBeA(Recipe.class);
    }


    public void parse(int nr) {
        ArrayList<String> fileContent = new ArrayList<>();
        fileContent.add(rawRecipeList.get(nr));
        try {
            recipe = ckParser.parse(fileContent);
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Fehler in Test " + nr);
        }
    }

    @Test
    public void testRecipeIngredientVariantFakeRecipe() {
        parse(0);
        the(recipe.getTitle()).shouldBeEqual("Zucchini-Pilz-Lasagne mit Feta-Topping");
        the(recipe.getPortions()).shouldBeEqual(4);
        the(recipe.getDuration()).shouldBeEqual(45);
        /*String text = "Zunächst heize man den Backofen auf ca. 175 Grad C vor.\n\nDann werden die zwei Grundsoßen zubereitet.\n\n" +
            "Für die erste nehme man klein geschnittene Zwiebeln und in Scheiben geschnittene Zucchini, Salz, Pfeffer, " +
            "Olivenöl und brate diese Zutaten in der Pfanne an, bis die Zucchini eine leichte Bräune zeigen. Man lösche " +
            "mit den gestückelten Tomaten ab, gebe etwas Gemüsebrühe dazu und gebe die Soße dann in eine Schüssel.\n\nIn der " +
            "Pfanne kann nun die zweite Soße bereitet werden. Dazu klein geschnittene Zwiebel, Pilzscheiben, Olivenöl und " +
            "Salz anbraten, bis wiederum die Pilze eine leichte Bräune zeigen. Dann füge man das Tessiner Gewürz oder andere " +
            "Gewürze hinzu. Mit der Mandelsahne oder Sojasahne wird abgelöscht.\n\nIn die Auflaufform gebe man zuerst von der " +
            "roten Soße etwas hinein, dann die Lasagneplatten, dann die helle Soße usw., bis alles verbraucht ist. Klein " +
            "gekrümelter Feta kommt obenauf.\n\n25 min. bei etwa 175 Grad C im Backofen backen, bis der Feta etwas braun ist.";*/
        String text = "Zunächst heize man den Backofen auf ca. 175 Grad C vor. Dann werden die zwei Grundsoßen zubereitet. " +
            "Für die erste nehme man klein geschnittene Zwiebeln und in Scheiben geschnittene Zucchini, Salz, Pfeffer, " +
            "Olivenöl und brate diese Zutaten in der Pfanne an, bis die Zucchini eine leichte Bräune zeigen. Man lösche " +
            "mit den gestückelten Tomaten ab, gebe etwas Gemüsebrühe dazu und gebe die Soße dann in eine Schüssel. In der " +
            "Pfanne kann nun die zweite Soße bereitet werden. Dazu klein geschnittene Zwiebel, Pilzscheiben, Olivenöl und " +
            "Salz anbraten, bis wiederum die Pilze eine leichte Bräune zeigen. Dann füge man das Tessiner Gewürz oder andere " +
            "Gewürze hinzu. Mit der Mandelsahne oder Sojasahne wird abgelöscht. In die Auflaufform gebe man zuerst von der " +
            "roten Soße etwas hinein, dann die Lasagneplatten, dann die helle Soße usw., bis alles verbraucht ist. Klein " +
            "gekrümelter Feta kommt obenauf. 25 min. bei etwa 175 Grad C im Backofen backen, bis der Feta etwas braun ist.";
        //the(recipe.getText()).shouldBeEqual(text);

        the(recipe.getIngredients().get(0).getLeft().getName()).shouldBeEqual("Lasagneplatte(n)");
        the(recipe.getIngredients().get(0).getMiddle()).shouldBeEqual(9.0);
        the(recipe.getIngredients().get(0).getRight().getName()).shouldBeEqual("");

        the(recipe.getIngredients().get(1).getLeft().getName()).shouldBeEqual("Champignons");
        the(recipe.getIngredients().get(1).getMiddle()).shouldBeEqual(300.0);
        the(recipe.getIngredients().get(1).getRight().getName()).shouldBeEqual("g");

        the(recipe.getIngredients().get(2).getLeft().getName()).shouldBeEqual("Champignons");
        the(recipe.getIngredients().get(2).getMiddle()).shouldBeEqual(100.0);
        the(recipe.getIngredients().get(2).getRight().getName()).shouldBeEqual("g");

        the(recipe.getIngredients().get(3).getLeft().getName()).shouldBeEqual("Champignons");
        the(recipe.getIngredients().get(3).getMiddle()).shouldBeEqual(2.0);
        the(recipe.getIngredients().get(3).getRight().getName()).shouldBeEqual("");

        the(recipe.getIngredients().get(4).getLeft().getName()).shouldBeEqual("Champignons");
        the(recipe.getIngredients().get(4).getMiddle()).shouldBeEqual(3.1);
        the(recipe.getIngredients().get(4).getRight().getName()).shouldBeEqual("g");

        the(recipe.getIngredients().get(5).getLeft().getName()).shouldBeEqual("Champignons");
        the(recipe.getIngredients().get(5).getMiddle()).shouldBeEqual(3.2);
        the(recipe.getIngredients().get(5).getRight().getName()).shouldBeEqual("g");

        the(recipe.getIngredients().get(6).getLeft().getName()).shouldBeEqual("Champignons");
        the(recipe.getIngredients().get(6).getMiddle()).shouldBeEqual(3.3);
        the(recipe.getIngredients().get(6).getRight().getName()).shouldBeEqual("g");

        the(recipe.getIngredients().get(7).getLeft().getName()).shouldBeEqual("Champignons");
        the(recipe.getIngredients().get(7).getMiddle()).shouldBeEqual(3.4);
        the(recipe.getIngredients().get(7).getRight().getName()).shouldBeEqual("g");

        the(recipe.getIngredients().get(8).getLeft().getName()).shouldBeEqual("Champignons");
        the(recipe.getIngredients().get(8).getMiddle()).shouldBeEqual(4.123);
        the(recipe.getIngredients().get(8).getRight().getName()).shouldBeEqual("g");

        the(recipe.getIngredients().get(9).getLeft().getName()).shouldBeEqual("Champignons");
        the(recipe.getIngredients().get(9).getMiddle()).shouldBeEqual(4.123);
        the(recipe.getIngredients().get(9).getRight().getName()).shouldBeEqual("g");

        the(recipe.getIngredients().get(10).getLeft().getName()).shouldBeEqual("Champignons");
        the(recipe.getIngredients().get(10).getMiddle()).shouldBeEqual(4.124);
        the(recipe.getIngredients().get(10).getRight().getName()).shouldBeEqual("g");

        the(recipe.getIngredients().get(11).getLeft().getName()).shouldBeEqual("Champignons");
        the(recipe.getIngredients().get(11).getMiddle()).shouldBeEqual(4.224);
        the(recipe.getIngredients().get(11).getRight().getName()).shouldBeEqual("g");

        the(recipe.getIngredients().get(12).getLeft().getName()).shouldBeEqual("Champignons1adsfasfaregfadgragafadgagadgfs012");
        the(recipe.getIngredients().get(12).getMiddle()).shouldBeEqual(300.0);
        the(recipe.getIngredients().get(12).getRight().getName()).shouldBeEqual("g1asfdsagfdsgagfgfdagasgsgadsgsggsgfagdäkg012");

        the(recipe.getIngredients().get(13).getLeft().getName()).shouldBeEqual("Champignons1adsfasfaregfadgragafadgagadgfs012");
        the(recipe.getIngredients().get(13).getMiddle()).shouldBeEqual(300.0);
        the(recipe.getIngredients().get(13).getRight().getName()).shouldBeEqual("g1asfdsagfdsgagfgfdagasgsgadsgsggsgfagdäkg012");

        the(recipe.getIngredients().get(14).getLeft().getName()).shouldBeEqual("Zucchini");
        the(recipe.getIngredients().get(14).getMiddle()).shouldBeEqual(3.0);
        the(recipe.getIngredients().get(14).getRight().getName()).shouldBeEqual("");

        the(recipe.getIngredients().get(15).getLeft().getName()).shouldBeEqual("Tomate(n) , geschält und gestückelt");
        the(recipe.getIngredients().get(15).getMiddle()).shouldBeEqual(1.0);
        the(recipe.getIngredients().get(15).getRight().getName()).shouldBeEqual("Dose");

        the(recipe.getIngredients().get(16).getLeft().getName()).shouldBeEqual("etwas Olivenöl");
        the(recipe.getIngredients().get(16).getMiddle()).shouldBeEqual(0.0);
        the(recipe.getIngredients().get(16).getRight().getName()).shouldBeEqual("");

        the(recipe.getIngredients().get(17).getLeft().getName()).shouldBeEqual("Zwiebel(n)");
        the(recipe.getIngredients().get(17).getMiddle()).shouldBeEqual(2.0);
        the(recipe.getIngredients().get(17).getRight().getName()).shouldBeEqual("");

        the(recipe.getIngredients().get(18).getLeft().getName()).shouldBeEqual("n. B. Gewürz(e) , z.B. Tessiner Gewürz");
        the(recipe.getIngredients().get(18).getMiddle()).shouldBeEqual(0.0);
        the(recipe.getIngredients().get(18).getRight().getName()).shouldBeEqual("");

        the(recipe.getIngredients().get(19).getLeft().getName()).shouldBeEqual("Mandelsahne , oder Sojasahne");
        the(recipe.getIngredients().get(19).getMiddle()).shouldBeEqual(1.0);
        the(recipe.getIngredients().get(19).getRight().getName()).shouldBeEqual("Becher");

        the(recipe.getIngredients().get(20).getLeft().getName()).shouldBeEqual("etwas Gemüsebrühepulver");
        the(recipe.getIngredients().get(20).getMiddle()).shouldBeEqual(0.0);
        the(recipe.getIngredients().get(20).getRight().getName()).shouldBeEqual("");

        the(recipe.getIngredients().get(21).getLeft().getName()).shouldBeEqual("etwas Salz und Pfeffer");
        the(recipe.getIngredients().get(21).getMiddle()).shouldBeEqual(0.0);
        the(recipe.getIngredients().get(21).getRight().getName()).shouldBeEqual("");

        the(recipe.getIngredients().get(22).getLeft().getName()).shouldBeEqual("Feta-Käse");
        the(recipe.getIngredients().get(22).getMiddle()).shouldBeEqual(100.0);
        the(recipe.getIngredients().get(22).getRight().getName()).shouldBeEqual("g");
    }

    @Test
    public void testRecipeExample1() {
        parse(1);
        the(recipe.getTitle()).shouldBeEqual("Zucchini - Lasagne");
        the(recipe.getPortions()).shouldBeEqual(4);
        the(recipe.getDuration()).shouldBeEqual(40);
        /*String text = "Zucchini waschen und längs in fingerdicke Scheiben schneiden. In einer Pfanne in Olivenöl von beiden " +
            "Seiten anbraten, auf Küchenpapier abtropfen lassen. Oder die Scheiben mit Olivenöl bestreichen, ein wenig salzen " +
            "und auf der obersten Schiene mit der Grillfunktion im Backofen bräunen. Das dauert allerdings länger.\n\n" +
            "Die Zwiebel in Würfel schneiden und in der Pfanne in wenig Olivenöl glasig dünsten. Knoblauchzehe dazu pressen und " +
            "etwas mitdünsten. Das Hackfleisch hinzugeben und krümelig anbraten. Wenn das Fleisch Farbe bekommen hat, mit Salz, " +
            "Pfeffer und Paprikapulver würzen, 1 EL Tomatenmark hinzugeben, unterrühren und eine Minute anschwitzen. Tomaten " +
            "hinzugeben und mit Oregano, Thymian, Salz, Pfeffer, Paprika würzen. 10 min auf kleiner Flamme köcheln lassen, zum " +
            "Schluss die gehackte Petersilie hinzugeben.\n\nFrischkäse mit der Milch verrühren,  wahlweise auch Sauerrahm " +
            "unterrühren. Mit Salz, Pfeffer und etwas Muskat würzen, ca. 50 g Streukäse unterrühren.\n\nEine Lasagneform oder eine " +
            "andere Auflaufform  mit Zucchinischeiben auslegen. Darauf ein paar Löffel Tomatenhacksoße verteilen, darauf eine " +
            "Schicht Frischkäsesoße, und darauf wieder Zucchinischeiben. Weiter so schichten, bis alle Zutaten verbraucht sind. " +
            "Die oberste Schicht soll Tomatenhacksoße sein. Diese mit dem restlichen Käse bestreuen und im auf 200 Grad C vorgeheizten " +
            "Ofen ca. 30 min goldbraun backen.\n\nDazu passt ein kleiner frischer Salat.";*/
        String text = "Zucchini waschen und längs in fingerdicke Scheiben schneiden. In einer Pfanne in Olivenöl von beiden " +
            "Seiten anbraten, auf Küchenpapier abtropfen lassen. Oder die Scheiben mit Olivenöl bestreichen, ein wenig salzen " +
            "und auf der obersten Schiene mit der Grillfunktion im Backofen bräunen. Das dauert allerdings länger." +
            "Die Zwiebel in Würfel schneiden und in der Pfanne in wenig Olivenöl glasig dünsten. Knoblauchzehe dazu pressen und " +
            "etwas mitdünsten. Das Hackfleisch hinzugeben und krümelig anbraten. Wenn das Fleisch Farbe bekommen hat, mit Salz, " +
            "Pfeffer und Paprikapulver würzen, 1 EL Tomatenmark hinzugeben, unterrühren und eine Minute anschwitzen. Tomaten " +
            "hinzugeben und mit Oregano, Thymian, Salz, Pfeffer, Paprika würzen. 10 min auf kleiner Flamme köcheln lassen, zum " +
            "Schluss die gehackte Petersilie hinzugeben.Frischkäse mit der Milch verrühren,  wahlweise auch Sauerrahm " +
            "unterrühren. Mit Salz, Pfeffer und etwas Muskat würzen, ca. 50 g Streukäse unterrühren.Eine Lasagneform oder eine " +
            "andere Auflaufform  mit Zucchinischeiben auslegen. Darauf ein paar Löffel Tomatenhacksoße verteilen, darauf eine " +
            "Schicht Frischkäsesoße, und darauf wieder Zucchinischeiben. Weiter so schichten, bis alle Zutaten verbraucht sind. " +
            "Die oberste Schicht soll Tomatenhacksoße sein. Diese mit dem restlichen Käse bestreuen und im auf 200 Grad C vorgeheizten " +
            "Ofen ca. 30 min goldbraun backen.Dazu passt ein kleiner frischer Salat.";
        //the(recipe.getText()).shouldBeEqual(text);

        the(recipe.getIngredients().get(0).getLeft().getName()).shouldBeEqual("Zucchini , große, dicke");
        the(recipe.getIngredients().get(0).getMiddle()).shouldBeEqual(1.0);
        the(recipe.getIngredients().get(0).getRight().getName()).shouldBeEqual("kg");

        the(recipe.getIngredients().get(1).getLeft().getName()).shouldBeEqual("Hackfleisch , vom Rind");
        the(recipe.getIngredients().get(1).getMiddle()).shouldBeEqual(500.0);
        the(recipe.getIngredients().get(1).getRight().getName()).shouldBeEqual("g");

        the(recipe.getIngredients().get(2).getLeft().getName()).shouldBeEqual("Zwiebel(n)");
        the(recipe.getIngredients().get(2).getMiddle()).shouldBeEqual(1.0);
        the(recipe.getIngredients().get(2).getRight().getName()).shouldBeEqual("große");

        the(recipe.getIngredients().get(3).getLeft().getName()).shouldBeEqual("Knoblauchzehe(n)");
        the(recipe.getIngredients().get(3).getMiddle()).shouldBeEqual(1.0);
        the(recipe.getIngredients().get(3).getRight().getName()).shouldBeEqual("dicke");

        the(recipe.getIngredients().get(4).getLeft().getName()).shouldBeEqual("Tomate(n) , stückig");
        the(recipe.getIngredients().get(4).getMiddle()).shouldBeEqual(1.0);
        the(recipe.getIngredients().get(4).getRight().getName()).shouldBeEqual("Dose");

        the(recipe.getIngredients().get(5).getLeft().getName()).shouldBeEqual("Frischkäse");
        the(recipe.getIngredients().get(5).getMiddle()).shouldBeEqual(1.0);
        the(recipe.getIngredients().get(5).getRight().getName()).shouldBeEqual("Pck.");

        the(recipe.getIngredients().get(6).getLeft().getName()).shouldBeEqual("Milch");
        the(recipe.getIngredients().get(6).getMiddle()).shouldBeEqual(100.0);
        the(recipe.getIngredients().get(6).getRight().getName()).shouldBeEqual("ml");

        the(recipe.getIngredients().get(7).getLeft().getName()).shouldBeEqual("n. B. Sauerrahm");
        the(recipe.getIngredients().get(7).getMiddle()).shouldBeEqual(0.0);
        the(recipe.getIngredients().get(7).getRight().getName()).shouldBeEqual("");

        the(recipe.getIngredients().get(8).getLeft().getName()).shouldBeEqual("Tomatenmark");
        the(recipe.getIngredients().get(8).getMiddle()).shouldBeEqual(1.0);
        the(recipe.getIngredients().get(8).getRight().getName()).shouldBeEqual("EL");

        the(recipe.getIngredients().get(9).getLeft().getName()).shouldBeEqual("Käse , gerieben");
        the(recipe.getIngredients().get(9).getMiddle()).shouldBeEqual(150.0);
        the(recipe.getIngredients().get(9).getRight().getName()).shouldBeEqual("g");

        the(recipe.getIngredients().get(10).getLeft().getName()).shouldBeEqual("Olivenöl");
        the(recipe.getIngredients().get(10).getMiddle()).shouldBeEqual(0.0);
        the(recipe.getIngredients().get(10).getRight().getName()).shouldBeEqual("");

        the(recipe.getIngredients().get(11).getLeft().getName()).shouldBeEqual("Oregano");
        the(recipe.getIngredients().get(11).getMiddle()).shouldBeEqual(0.0);
        the(recipe.getIngredients().get(11).getRight().getName()).shouldBeEqual("");

        the(recipe.getIngredients().get(12).getLeft().getName()).shouldBeEqual("Thymian");
        the(recipe.getIngredients().get(12).getMiddle()).shouldBeEqual(0.0);
        the(recipe.getIngredients().get(12).getRight().getName()).shouldBeEqual("");

        the(recipe.getIngredients().get(13).getLeft().getName()).shouldBeEqual("Petersilie");
        the(recipe.getIngredients().get(13).getMiddle()).shouldBeEqual(0.0);
        the(recipe.getIngredients().get(13).getRight().getName()).shouldBeEqual("");

        the(recipe.getIngredients().get(14).getLeft().getName()).shouldBeEqual("Salz und Pfeffer");
        the(recipe.getIngredients().get(14).getMiddle()).shouldBeEqual(0.0);
        the(recipe.getIngredients().get(14).getRight().getName()).shouldBeEqual("");

        the(recipe.getIngredients().get(15).getLeft().getName()).shouldBeEqual("Paprika , edelsüß");
        the(recipe.getIngredients().get(15).getMiddle()).shouldBeEqual(0.0);
        the(recipe.getIngredients().get(15).getRight().getName()).shouldBeEqual("");
    }
}
