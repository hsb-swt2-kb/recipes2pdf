package sample.parser;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.javalite.test.jspec.JSpec.expect;
import static org.javalite.test.jspec.JSpec.the;

import org.junit.rules.ExpectedException;
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
        the(recipe.getIngredients().get(0).getMiddle()).shouldBeEqual(0);;
        the(recipe.getIngredients().get(0).getRight().getName()).shouldBeNull();
    }
    @Test
    public void testParseMethod19() {
        Setup("TestGericht19.txt");
        the(recipe.getIngredients().get(0).getLeft().getName()).shouldBeEqual("Salz (grob)");
        the(recipe.getIngredients().get(0).getMiddle()).shouldBeEqual(0);;
        the(recipe.getIngredients().get(0).getRight().getName()).shouldBeNull();
    }
    @Test
    public void testParseMethod20() {
        Setup("TestGericht20.txt");
        the(recipe.getIngredients().get(0).getLeft().getName()).shouldBeEqual("Thunfisch eingelegt in Olivenöl");
        the(recipe.getIngredients().get(0).getMiddle()).shouldBeEqual(3);
        the(recipe.getIngredients().get(0).getRight().getName()).shouldBeEqual("Dosen");
    }
    @Test
    public void testParseMethod21() {
        Setup("TestGericht21.txt");
        the(recipe.getIngredients().get(0).getLeft().getName()).shouldBeEqual("Tomaten");
        the(recipe.getIngredients().get(0).getMiddle()).shouldBeEqual(2);
        the(recipe.getIngredients().get(0).getRight().getName()).shouldBeNull();
        the(recipe.getIngredients().get(1).getLeft().getName()).shouldBeEqual("Salz");
        the(recipe.getIngredients().get(1).getMiddle()).shouldBeEqual(0);
        the(recipe.getIngredients().get(1).getRight().getName()).shouldBeNull();
    }
    @Test
    public void testParseMethod22() {
        Setup("TestGericht22.txt");
        the(recipe.getIngredients().get(0).getLeft().getName()).shouldBeEqual("Tomaten");
        the(recipe.getIngredients().get(0).getMiddle()).shouldBeEqual(2);
        the(recipe.getIngredients().get(0).getRight().getName()).shouldBeNull();
        the(recipe.getIngredients().get(1).getLeft().getName()).shouldBeEqual("Salz");
        the(recipe.getIngredients().get(1).getMiddle()).shouldBeEqual(0);
        the(recipe.getIngredients().get(1).getRight().getName()).shouldBeNull();
    }
    @Test
    public void testParseMethod23() {
        Setup("TestGericht23.txt");
        the(recipe.getIngredients().get(0).getLeft().getName()).shouldBeEqual("Tomaten");
        the(recipe.getIngredients().get(0).getMiddle()).shouldBeEqual(2);
        the(recipe.getIngredients().get(0).getRight().getName()).shouldBeNull();
    }
    @Test
    public void testParseMethod24() {
        Setup("TestGericht24.txt");
        the(recipe.getIngredients().get(0).getLeft().getName()).shouldBeEqual("Tomaten");
        the(recipe.getIngredients().get(0).getMiddle()).shouldBeEqual(2);
        the(recipe.getIngredients().get(0).getRight().getName()).shouldBeNull();
    }
    @Test
    public void testParseMethod25() {
        Setup("TestGericht25.txt");
        the(recipe.getIngredients().get(0).getLeft().getName()).shouldBeEqual("Tomaten");
        the(recipe.getIngredients().get(0).getMiddle()).shouldBeEqual(2);
        the(recipe.getIngredients().get(0).getRight().getName()).shouldBeNull();
    }
    @Test
    public void testParseMethod26() {
        Setup("TestGericht26.txt");
        the(recipe.getIngredients().size()).shouldBeEqual(0);
    }
    @Test
    public void testParseMethod27() {
        Setup("TestGericht27.txt");
        the(recipe.getIngredients().size()).shouldBeEqual(0);
    }
    @Test
    public void testParseMethod28() {
        Setup("TestGericht28.txt");
        the(recipe.getIngredients().get(0).getLeft().getName()).shouldBeEqual("Kartoffeln");
        the(recipe.getIngredients().get(0).getMiddle()).shouldBeEqual(1.850);
        the(recipe.getIngredients().get(0).getRight().getName()).shouldBeEqual("kg");
    }
    @Test
    public void testParseMethod29() {
        Setup("TestGericht29.txt");
        the(recipe.getIngredients().get(0).getLeft().getName()).shouldBeEqual("Eine Zutatsbezeichnung mit 44 Zeichen1234567");
        the(recipe.getIngredients().get(0).getMiddle()).shouldBeEqual(2.5);
        the(recipe.getIngredients().get(0).getRight().getName()).shouldBeEqual("TL");
    }
    @Test
    public void testParseMethod30() {
        Setup("TestGericht30.txt");
        the(recipe.getIngredients().get(0).getLeft().getName()).shouldBeEqual("Eine Zutatsbezeichnung mit 45 Zeichen12345678");
        the(recipe.getIngredients().get(0).getMiddle()).shouldBeEqual(2.5);
        the(recipe.getIngredients().get(0).getRight().getName()).shouldBeEqual("TL");
    }
    @Test
    public void testParseMethod31() {
        Setup("TestGericht31.txt");
        the(recipe.getIngredients().get(0).getLeft().getName()).shouldBeEqual("Eine Zutatsbezeichnung mit 46 Zeichen12345678");
        the(recipe.getIngredients().get(0).getMiddle()).shouldBeEqual(2.5);
        the(recipe.getIngredients().get(0).getRight().getName()).shouldBeEqual("TL");
    }
    @Test
    public void testParseMethod32() {
        Setup("TestGericht32.txt");
        the(recipe.getIngredients().get(0).getLeft().getName()).shouldBeEqual("Tomaten");
        the(recipe.getIngredients().get(0).getMiddle()).shouldBeEqual(2.5);
        the(recipe.getIngredients().get(0).getRight().getName()).shouldBeEqual("EineEinheitsbezeichnungMit44Zeichen123456789");
    }
    @Test
    public void testParseMethod33() {
        Setup("TestGericht33.txt");
        the(recipe.getIngredients().get(0).getLeft().getName()).shouldBeEqual("Tomaten");
        the(recipe.getIngredients().get(0).getMiddle()).shouldBeEqual(2.5);
        the(recipe.getIngredients().get(0).getRight().getName()).shouldBeEqual("EineEinheitsbezeichnungMit45Zeichen1234567890");
    }
    @Test
    public void testParseMethod34() {
        Setup("TestGericht34.txt");
        the(recipe.getIngredients().get(0).getLeft().getName()).shouldBeEqual("Tomaten");
        the(recipe.getIngredients().get(0).getMiddle()).shouldBeEqual(2.5);
        the(recipe.getIngredients().get(0).getRight().getName()).shouldBeEqual("EineEinheitsbezeichnungMit46Zeichen1234567890");
    }
    @Test
    public void testParseMethod35() {
        Setup("TestGericht35.txt");
        the(recipe.getIngredients().get(0).getLeft().getName()).shouldBeEqual("Zucker");
        the(recipe.getIngredients().get(0).getMiddle()).shouldBeType(Double.class);
        the(recipe.getIngredients().get(0).getRight().getName()).shouldBeEqual("pg");
    }
    @Test
    public void testParseMethod36() {
        Setup("TestGericht36.txt");
        the(recipe.getIngredients().get(0).getLeft().getName()).shouldBeEqual("Zucker");
        the(recipe.getIngredients().get(0).getMiddle()).shouldBeType(Double.class);
        the(recipe.getIngredients().get(0).getRight().getName()).shouldBeEqual("pg");
    }
    @Test
    public void testParseMethod37() {
        Setup("TestGericht37.txt");
        the(recipe.getIngredients().get(0).getLeft().getName()).shouldBeEqual("Zucker");
        the(recipe.getIngredients().get(0).getMiddle()).shouldBeType(Double.class);
        the(recipe.getIngredients().get(0).getRight().getName()).shouldBeEqual("pg");
    }
    @Test
    public void testParseMethod38() {
        Setup("TestGericht38.txt");
        the(recipe.getText()).shouldBeEqual("Kochen Sie die Tomaten!");
    }
    @Test
    public void testParseMethod39() {
        Setup("TestGericht39.txt");
        the(recipe.getText()).shouldBeEqual("Kochen Sie die Tomaten!");
    }
    @Test
    public void testParseMethod40() {
        Setup("TestGericht40.txt");
        the(recipe.getText()).shouldBeEqual("");
    }
    @Test
    public void testParseMethod41() {
        Setup("TestGericht41.txt");
        the(recipe.getText()).shouldBeEqual("Kochen Sie die Tomaten!");
    }
    @Test
    public void testParseMethod42() {
        Setup("TestGericht42.txt");
        the(recipe.getText()).shouldBeEqual("Kochen Sie die Tomaten!");
    }
    @Test
    public void testParseMethod43() {
        Setup("TestGericht43.txt");
        the(recipe.getText()).shouldBeEqual("Kochen Sie die Tomaten!");
    }
    @Test
    public void testParseMethod45() {
        Setup("TestGericht45.txt");
        the(recipe.getPortions()).shouldBeEqual(4);
        the(recipe.getPortions()).shouldBeType(Integer.class);
    }
    @Test
    public void testParseMethod46() {
        Setup("TestGericht46.txt");
        the(recipe.getPortions()).shouldBeEqual(4);
        the(recipe.getPortions()).shouldBeType(Integer.class);
    }
    @Test
    public void testParseMethod47() {
        Setup("TestGericht47.txt");
        the(recipe.getPortions()).shouldBeEqual(0);
        the(recipe.getPortions()).shouldBeType(Integer.class);
    }
    @Test
    public void testParseMethod48() {
        Setup("TestGericht48.txt");
        the(recipe.getPortions()).shouldBeEqual(0);
        the(recipe.getPortions()).shouldBeType(Integer.class);
    }
    @Test
    public void testParseMethod49() {
        Setup("TestGericht49.txt");
        the(recipe.getPortions()).shouldBeEqual(4);
        the(recipe.getPortions()).shouldBeType(Integer.class);
    }
    @Test
    public void testParseMethod50() {
        Setup("TestGericht50.txt");
        the(recipe.getPortions()).shouldBeEqual(4);
        the(recipe.getPortions()).shouldBeType(Integer.class);
    }
    @Test
    public void testParseMethod51() {
        Setup("TestGericht51.txt");
        the(recipe.getCategory().getName()).shouldBeEqual("Hauptgericht");
    }
    @Test
    public void testParseMethod52() {
        Setup("TestGericht51.txt");
        the(recipe.getCourse().getName()).shouldBeEqual("Reis");
    }
    @Test
    public void testParseMethod53() {
        Setup("TestGericht51.txt");
        the(recipe.getRegion().getName()).shouldBeEqual("Japan");
    }
    @Test
    public void testParseMethod54() {
        Setup("TestGericht51.txt");
        the(recipe.getDaytime().getName()).shouldBeEqual("Mittag");
    }
    @Test
    public void testParseMethod55() {
        Setup("TestGericht51.txt");
        the(recipe.getSeason().getName()).shouldBeEqual("Sommer");
    }
    @Test
    public void testParseMethod56() {
        Setup("TestGericht51.txt");
        the(recipe.getNurture().getName()).shouldBeEqual("Vegan");
    }
    @Test
    public void testParseMethod57() {
        Setup("TestGericht51.txt");
        the(recipe.getCalories()).shouldBeEqual(300);
    }
    @Test
    public void testParseMethod57a() {
        Setup("TestGericht57a.txt");
        the(recipe.getCalories()).shouldBeEqual(300);
    }
    @Test
    public void testParseMethod58() {
        Setup("TestGericht51.txt");
        the(recipe.getDuration()).shouldBeEqual(40);
    }
    @Test
    public void testParseMethod59() {
        Setup("TestGericht59.txt");
        the(recipe.getDuration()).shouldBeEqual(40);
    }
    @Test
    public void testParseMethod60() {
        Setup("TestGericht60.txt");
        the(recipe.getDuration()).shouldBeEqual(40);
    }
    @Test
    public void testParseMethod61() {
        Setup("TestGericht61.txt");
        the(recipe.getDuration()).shouldBeEqual(40);
    }
    @Test
    public void testParseMethod62() {
        Setup("TestGericht62.txt");
        the(recipe.getDuration()).shouldBeEqual(40);
    }
    @Test
    public void testParseMethod63() {
        Setup("TestGericht63.txt");
        the(recipe.getDuration()).shouldBeEqual(40);
    }
    @Test
    public void testParseMethod64() {
        Setup("TestGericht64.txt");
        the(recipe.getDuration()).shouldBeEqual(120);
    }
    @Test
    public void testParseMethod65() {
        Setup("TestGericht65.txt");
        the(recipe.getDuration()).shouldBeEqual(120);
    }
    @Test
    public void testParseMethod66() {
        Setup("TestGericht66.txt");
        the(recipe.getDuration()).shouldBeEqual(120);
    }
    @Test
    public void testParseMethod67() {
        Setup("TestGericht67.txt");
        the(recipe.getDuration()).shouldBeEqual(120);
    }
    @Test
    public void testParseMethod43a() {
        Setup("TestGericht43a.txt");
        the(recipe.getText().length()).shouldBeEqual(4241);
    }
    @Test
    public void testParseMethod43b() {
        Setup("TestGericht43b.txt");
        the(recipe.getText().length()).shouldBeEqual(4242);
    }
    @Test
    public void testParseMethod43c() {
        Setup("TestGericht43c.txt");
        the(recipe.getText().length()).shouldBeEqual(4242);
    }





}
