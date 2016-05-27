package sample.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Henrik
 * last changed on 02.05.2016
 */
abstract class AConcreteParser implements Constants,IConcreteParser {

  public String readFilecontent(String absoluterDateiPfad) throws IOException
  {
    String  thisFilerow = null;
    ArrayList<String> fileContent = new ArrayList<String>();

    FileReader fr = new FileReader(absoluterDateiPfad);
    BufferedReader br = new BufferedReader(fr);
    while ((thisFilerow = br.readLine()) != null)
    {
      fileContent.add(thisFilerow);
    }
    fr.close();
    br.close();

    return fileContent.toString();
  }
}
