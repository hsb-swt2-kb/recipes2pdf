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


    public void Setup(String Datei){
        try{
          AL = txt.readFilecontent(workpath+"/recipes2pdf/src/test/resources/sample/parser/"+Datei);
          recipe = txt.parse(AL);
        }
        catch(FileNotFoundException e){
          e.printStackTrace();
          System.out.println("Datei nicht gefunden");
        }
        catch(IOException e){
          System.out.println("Datei Fehlerhaft");
        }
    }
    @Test
    public void testParseMethod(){
        Setup("TestGericht.txt");
        the(recipe.getTitle()).shouldBeEqual("Vegetarische Lasagne");
    }
    @Test
    public void testParseMethod2(){
        Setup("TestGericht2.txt");
        the(recipe.getTitle()).shouldBeEqual("Vegetarische Lasagne");
    }
    @Test
    public void testParseMethod3(){
        Setup("TestGericht3.txt");
        the(recipe.getTitle()).shouldBeEqual("Vegetarische Lasagne");
    }
    @Test
    public void testParseMethod4(){
        Setup("TestGericht4.txt");
        the(recipe.getTitle()).shouldBeEqual("Vegetarische Lasagne");
    }
    @Test
    public void testParseMethod5(){
        Setup("TestGericht5.txt");
        the(recipe.getTitle()).shouldBeEqual("Vegetarische Lasagne");
    }
    @Test
    public void testParseMethod6(){
        Setup("TestGericht6.txt");
        the(recipe.getTitle()).shouldBeEqual("Vegetarische Lasagne");
    }
    @Test
    public void testParseMethod7(){
        Setup("TestGericht7.txt");
        the(recipe.getTitle()).shouldBeEqual("Vegetarische Lasagne");
    }
    @Test
    public void testParseMethod8(){
        Setup("TestGericht8.txt");
        the(recipe.getTitle()).shouldBeEqual("Vegetarische Lasagne");
    }
}
