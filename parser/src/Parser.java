
import java.io.BufferedReader;
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
public class Parser implements Konstanten
{
  
  
  public ArrayList<String> leseDateiInhalt(String absoluterDateiPfad) throws IOException
  {
    String  dieseZeile = null;
    ArrayList<String> dateiinhalt = new ArrayList<String>();
    
    FileReader fr = new FileReader(absoluterDateiPfad); 
    BufferedReader br = new BufferedReader(fr);
    while ((dieseZeile = br.readLine()) != null) 
    {
      dateiinhalt.add(dieseZeile);
    }
    fr.close();
    br.close();
    
    return dateiinhalt;
  }
}
