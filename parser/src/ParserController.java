import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Henrik
 */
public class ParserController {
  
  
  public static void main(String[] args) throws IOException
  {
    Gericht a = new Gericht();
    String  dieseZeile = null;
    TxTParser textParser = new TxTParser();
    //HTMLParser htmlParser = new HTMLParser();
    String dateipfad = "E:/Seafile/Studium/04_SoftwaretechnikII/recipes2pdf/parser/src/TestGericht.txt";
    
    //String dateipfad = "E:/Seafile/Studium/04_SoftwaretechnikII/recipes2pdf/parser/src/Arroz-con-costillas.html";
   
    //ArrayList<String> dateiinhalt = htmlParser.leseDateiInhalt(dateipfad);
    ArrayList<String> dateiinhalt = textParser.leseDateiInhalt(dateipfad);
    
    //htmlParser.extrahiereDatenfeldAusTag(dateiinhalt);
   
    
    for(int i=0;i<dateiinhalt.size();i++)
    {
      System.out.println(dateiinhalt.get(i));
    

    //System.out.println(dateiinhalt);
   // System.out.println(dateiinhalt.size());
    
     String Zutaten = textParser.ermittleDatenfeld(dateiinhalt,"Zutaten");
    System.out.println(Zutaten);
    ArrayList<String[]> zutaten=textParser.extrahiereZutatenListeAusString(Zutaten);
    System.out.println(zutaten.size());
    System.out.println("Menge   |   Einheit    |    Zutat");
    for(int j=0;j<zutaten.size();j++)
    {
      System.out.println(zutaten.get(j)[0] +"   |   "+zutaten.get(j)[1]+"   |   "+zutaten.get(j)[2]);
    }
 

    
    
   // b.leseTxTDatei();
    
    
    
  
  }
  

  
  }}
  
  
