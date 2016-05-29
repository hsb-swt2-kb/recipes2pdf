package sample.parser;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.javalite.test.jspec.JSpec.the;

import sample.model.Recipe;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Henrik on 18.05.2016.
 */

public class TxtParserTest {
    Recipe recipe = new Recipe();
    TxtParser txt = new TxtParser();
    ArrayList<String> AL = new ArrayList<String>();
    String workpath = "E:/Seafile/Studium/04_SoftwaretechnikII";


    public void Setup(String Datei) {
        try {
            AL = txt.readFilecontent(workpath + "/recipes2pdf/src/test/resources/sample/parser/" + Datei);
            recipe = txt.parse(AL);
            //Output for simple debbuging. Do NOT delete @Markus :-)
            /*System.out.println("===========Neuer Testfall============");
            for(int j=0;j<recipe.getIngredients().size();j++)
            {
                System.out.println(recipe.getIngredients().get(j).getLeft().getName() +" | " +recipe.getIngredients().get(j).getMiddle() +" | " +recipe.getIngredients().get(j).getRight().getName());
            }*/

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Datei nicht gefunden");
        } catch (IOException e) {
            System.out.println("Datei Fehlerhaft");
        }
    }

    @Test
    public void testParseMethod() {
        Setup("TestGericht.txt");
        the(recipe.getTitle()).shouldBeEqual("Vegetarische Lasagne");
    }

    @Test
    public void testParseMethod2() {
        Setup("TestGericht2.txt");
        the(recipe.getTitle()).shouldBeEqual("Vegetarische Lasagne");
    }

    @Test
    public void testParseMethod3() {
        Setup("TestGericht3.txt");
        the(recipe.getTitle()).shouldBeEqual("Vegetarische Lasagne");
    }

    @Test
    public void testParseMethod4() {
        Setup("TestGericht4.txt");
        the(recipe.getTitle()).shouldBeEqual("Vegetarische Lasagne");
    }

    @Test
    public void testParseMethod5() {
        Setup("TestGericht5.txt");
        the(recipe.getTitle()).shouldBeEqual("Vegetarische Lasagne");
    }

    @Test
    public void testParseMethod6() {
        Setup("TestGericht6.txt");
        the(recipe.getTitle()).shouldBeEqual("Vegetarische Lasagne");
    }

    @Test
    public void testParseMethod7() {
        Setup("TestGericht7.txt");
        the(recipe.getTitle()).shouldBeEqual("Vegetarische Lasagne");
    }

    @Test
    public void testParseMethod8() {
        Setup("TestGericht8.txt");
        the(recipe.getTitle()).shouldBeEqual("Vegetarische Lasagne");
    }

    @Test
    public void testParseMethod9() {
        Setup("TestGericht9.txt");
        the(recipe.getIngredients().get(0).getLeft().getName()).shouldBeEqual("Zucker");
        the(recipe.getIngredients().get(0).getMiddle()).shouldBeEqual(2);
        the(recipe.getIngredients().get(0).getRight().getName()).shouldBeEqual("TL");
    }

    @Test
    public void testParseMethod10() {
        Setup("TestGericht10.txt");
        the(recipe.getIngredients().get(0).getLeft().getName()).shouldBeEqual("Zucker");
        the(recipe.getIngredients().get(0).getMiddle()).shouldBeEqual(2);
        the(recipe.getIngredients().get(0).getRight().getName()).shouldBeEqual("TL");
    }

    @Test
    public void testParseMethod11() {
        Setup("TestGericht11.txt");
        the(recipe.getIngredients().get(0).getLeft().getName()).shouldBeEqual("Zucker");
        the(recipe.getIngredients().get(0).getMiddle()).shouldBeEqual(2);
        the(recipe.getIngredients().get(0).getRight().getName()).shouldBeEqual("TL");
    }

    @Test
    public void testParseMethod12() {
        Setup("TestGericht12.txt");
        the(recipe.getIngredients().get(0).getLeft().getName()).shouldBeEqual("Zucker");
        the(recipe.getIngredients().get(0).getMiddle()).shouldBeEqual(2.5);
        the(recipe.getIngredients().get(0).getRight().getName()).shouldBeEqual("TL");
    }

    @Test
    public void testParseMethod13() {
        Setup("TestGericht13.txt");
        the(recipe.getIngredients().get(0).getLeft().getName()).shouldBeEqual("Zucker");
        the(recipe.getIngredients().get(0).getMiddle()).shouldBeEqual(2.5);
        the(recipe.getIngredients().get(0).getRight().getName()).shouldBeEqual("TL");
    }

    @Test
    public void testParseMethod14() {
        Setup("TestGericht14.txt");
        the(recipe.getIngredients().get(0).getLeft().getName()).shouldBeEqual("Zucker");
        the(recipe.getIngredients().get(0).getMiddle()).shouldBeEqual(2.5);
        the(recipe.getIngredients().get(0).getRight().getName()).shouldBeEqual("TL");
    }
    @Test
    public void testParseMethod15() {
        Setup("TestGericht15.txt");
        the(recipe.getIngredients().get(0).getLeft().getName()).shouldBeEqual("Zucker");
        the(recipe.getIngredients().get(0).getMiddle()).shouldBeEqual(2.5);
        the(recipe.getIngredients().get(0).getRight().getName()).shouldBeEqual("TL");
    }

    @Test
    public void testParseMethod16() {
        Setup("TestGericht16.txt");
        the(recipe.getIngredients().get(0).getLeft().getName()).shouldBeEqual("Zucker");
        the(recipe.getIngredients().get(0).getMiddle()).shouldBeEqual(2.5);
        the(recipe.getIngredients().get(0).getRight().getName()).shouldBeEqual("TL");
    }
    @Test
    public void testParseMethod17() {
        Setup("TestGericht17.txt");
        the(recipe.getIngredients().get(0).getLeft().getName()).shouldBeEqual("Tomaten");
        the(recipe.getIngredients().get(0).getMiddle()).shouldBeEqual(2);
        the(recipe.getIngredients().get(0).getRight().getName()).shouldBeNull();
    }
    @Test
    public void testParseMethod18() {
        Setup("TestGericht18.txt");
        the(recipe.getIngredients().get(0).getLeft().getName()).shouldBeEqual("Salz");
        the(recipe.getIngredients().get(0).getMiddle()).shouldBeEqual(0);
        the(recipe.getIngredients().get(0).getRight().getName()).shouldBeNull();
    }
    @Test
    public void testParseMethod19() {
        Setup("TestGericht19.txt");
        the(recipe.getIngredients().get(0).getLeft().getName()).shouldBeEqual("Salz (grob)");
        the(recipe.getIngredients().get(0).getMiddle()).shouldBeEqual(0);
        the(recipe.getIngredients().get(0).getRight().getName()).shouldBeNull();
    }
    @Test
    public void testParseMethod20() {
        Setup("TestGericht20.txt");
        the(recipe.getIngredients().get(0).getLeft().getName()).shouldBeEqual("Thunfisch eingelegt in Olivenöl");
        the(recipe.getIngredients().get(0).getMiddle()).shouldBeEqual(3);
        the(recipe.getIngredients().get(0).getRight().getName()).shouldBeEqual("Dosen");
    }


}
