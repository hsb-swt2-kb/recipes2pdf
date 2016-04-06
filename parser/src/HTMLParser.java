
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
public class HTMLParser extends Parser implements Konstanten {
  
  public Gericht befuelleGerichtsObjekt()
  {
    Gericht a = new Gericht();
    return a;
  }
  
  public void extrahiereDatenfeldAusTag(ArrayList<String> dateiinhalt)
  {
    for (int i=0;i<dateiinhalt.size();i++)
    {
      if(dateiinhalt.get(i).contains(Zutaten))
      {
        System.out.println(dateiinhalt.get(i));
      }
      
    
    
    
    
  }
  }}
