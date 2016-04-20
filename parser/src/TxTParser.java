import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TxTParser extends Parser implements Konstanten
{
  
  public Gericht befuelleGerichtsObjekt()
  {
    Gericht a = new Gericht();
    return a;
  }
  
  public String ermittleDatenfeld(ArrayList<String> textDateiInhalt,String signalwort)
  {
    String Daten = null;
    int zeileS = ermittleZeileSignalwort(textDateiInhalt,signalwort);
    
    if(zeileS >= 0)
    {
      int nZeile = ermittleZeileNaechstesSignalwort(zeileS,textDateiInhalt,signalwort);

      for(int i=zeileS;i<=nZeile;i++)
      {
        if(Daten==null)
        {
          Daten=textDateiInhalt.get(i);
        }
        else
        {
          Daten=Daten+textDateiInhalt.get(i);
        }      
      }
      Daten=Daten.replaceFirst(signalwort+":","");
      Daten=Daten.trim();
    }
    return Daten;
  }
  
  public int ermittleZeileSignalwort(ArrayList<String> textDateiInhalt,String signalwort)
  {
    for(int i=0;i<textDateiInhalt.size();i++)
    {
      if(textDateiInhalt.get(i).contains(signalwort+":"))
      {
        return i;
      }
    }
    return -1;
  }
    
  public int ermittleZeileNaechstesSignalwort(int j,ArrayList<String> textDateiInhalt,String signalwort)
  {
    for (int i=j+1;i<textDateiInhalt.size();i++)
    {
      for (int k=1;k<signalwoerter.length;k++)
      {
        if((textDateiInhalt.get(i).contains(signalwoerter[k]+":")) && signalwoerter[k]+":"!=signalwort)
        {
          return i-1;
        }
      }
    }
    return textDateiInhalt.size()-1;
  }        
  
  public String[] extrahiereAnzahlMenge(String s)
  { 
    String sTemp = s;
    String [] quanti = new String[2];
    quanti[0] = null;quanti[1] = null;

    try
    {
      //Ersetze "," durch "." zur Vereinfachung der Abfrage
      s=s.replaceFirst(",",".");
      
      //Extrahiere Double-Zahl auz String. Keine, eine oder mehrere Ziffern,
      //gefolgt von "." gefolgt von keinem, einem oder mehreren Nachkommastellen
      Pattern p = Pattern.compile("[-+]?([0-9]*\\.[0-9]+|[0-9]+)");
      Matcher m = p.matcher(s);
      
      while (m.find()) 
      {
        s=s.substring(m.start(), m.end());
      }

      //Wenn keine Zahlen gefunden, wird das Parsen eine Exception auslösen
      Double.parseDouble(s);
      
      quanti[0] = s;
      quanti[1] = sTemp.replaceFirst(s, null);
      
      return quanti;
    }
    catch (Exception e) { return quanti; }
  }
  
  public String baueStringAusArray(String[] s,int j)
  {
    String tempStr ="";
    for (int i = j;i<s.length;i++)
    {
      tempStr = tempStr+" "+s[i];
    }
    return tempStr.trim();
  }
  
  public ArrayList<String[]> extrahiereZutatenListeAusString(String zutaten)
  {
    boolean anzVorhanden;
    String zutatenArr[] = zutaten.split("-");
    ArrayList<String[]> zutatenliste = new ArrayList<String[]>();
    
    for(int i=1;i<zutatenArr.length;i++)
    {
      zutatenArr[i] = zutatenArr[i].trim();
      String zutatElement[] = zutatenArr[i].split(" ");
           
      String quanti[];
      quanti = extrahiereAnzahlMenge(zutatElement[0]);
   
      if (quanti[0] == null){ anzVorhanden = false; }
      else                  { anzVorhanden = true; }
            
      if (zutatElement.length==1 && anzVorhanden==false)
      { 
        //              Anzahl/Menge/Zutat
        String Zutat[] = {null,null,zutatElement[0]};
        zutatenliste.add(Zutat);
      }
      
      else if (zutatElement.length>=2 && anzVorhanden==false)
      { 
        String tempStr = baueStringAusArray(zutatElement,0);
        String Zutat[] = {quanti[0],quanti[1],tempStr};
        zutatenliste.add(Zutat);
      }
      
      else if (zutatElement.length==2 && anzVorhanden==true)
      {
        String Zutat[] = {quanti[0],quanti[1],zutatElement[1]};
        zutatenliste.add(Zutat);
      }
      
      else if (zutatElement.length==3 && anzVorhanden==true)
      {
         String Zutat[] = {quanti[0],zutatElement[1],zutatElement[2]};
         zutatenliste.add(Zutat);
      }
      
      else if (zutatElement.length>3 && anzVorhanden==true)
      {
        String tempStr = baueStringAusArray(zutatElement,2);
        String Zutat[] = {quanti[0],zutatElement[1],tempStr};
        zutatenliste.add(Zutat);
      }     
    }
  return zutatenliste;
  }  
}
