package sample.parser;

import java.io.IOException;
import java.util.ArrayList;

/*
 *
 * @author Henrik
 */
public class ParserController {


  public static void main(String[] args) throws IOException
  {
    Recipe a = new Recipe();
    TxtParser textParser = new TxtParser();

    String dateipfad = "E:/Seafile/Studium/04_SoftwaretechnikII/recipes2pdf.old/parser/src/TestGericht.txt";


    ArrayList<String> dateiinhalt = textParser.readFilecontent(dateipfad);

    textParser.parse(dateiinhalt);
  }
}


