package sample.parser;

import org.junit.Test;

import sample.model.Recipe;
import java.util.ArrayList;
/**
 * Created by Henrik on 18.05.2016.
 */

public class TxtParserTest {

    @Test
    public void testPArseMethod(){
        Recipe recipe = new Recipe();
        TxtParser txt = new TxtParser();

        ArrayList<String> AL = new ArrayList<String>();
        try
        {
            AL = txt.readFilecontent("E:/Seafile/Studium/04_SoftwaretechnikII/recipes2pdf/src/test/resources/sample/parser/TestGericht.txt");
            recipe = txt.parse(AL);
        }

        catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("Blaa");
        }


    }



}
