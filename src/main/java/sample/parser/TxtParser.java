package sample.parser;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* This Class can extract information for a recipe from a textfile

  precondition: A Arraylist of String, that contains information from a Textfile
  each row in the Arraxlist is one row in the textfile

  After exctracting all availible information, the recipe will be check for
  minimal data like recipename, Ingredientlist with at least one incredient
  and a preperationtext.

  All additional data like region of the recipe (ex. Greece, France, Africans etc..)
  have to be declared in the Textfile like "Region: France"

  Author: Henrik Giessel
  last edited: 3rd May of 2016
*/



public class TxtParser extends Parser implements Constants
{
  public Recipe parse(ArrayList<String> textFileContent) {
    Recipe recipe = new Recipe();

    //Try to extract minimal recipdata
    recipe.name = extractRecipename(textFileContent);
    recipe.zutaten = extractIncredentsList(textFileContent);
    recipe.zubereitung = findPreparationOfRecipe(textFileContent);

    //Try to exctract additional recipedata
    recipe.region = findDatafield(textFileContent,"Region");
    recipe.gerichtsart = findDatafield(textFileContent,"Gerichtsart");
    recipe.kategorie = findDatafield(textFileContent,"Kategorie");
    recipe.arbeitszeit = findDatafield(textFileContent,"Arbeitszeit");

    recipe.kcal = parseStringToNumeric(findDatafield(textFileContent,"Kcal"));
    recipe.portionen =  Double.parseDouble(findDatafield(textFileContent,"Portionen"));

    //Checking Data will be deletet later
    System.out.println(recipe.name);
    for (int i=0;i<recipe.zutaten.size();i++){
      System.out.println(recipe.zutaten.get(i)[0] +"   |   "+recipe.zutaten.get(i)[1]+"   |   "+recipe.zutaten.get(i)[2]);
    }
    System.out.println(recipe.zubereitung);
    System.out.println(recipe.region);
    System.out.println(recipe.gerichtsart);
    System.out.println(recipe.kategorie);
    System.out.println(recipe.arbeitszeit);
    System.out.println(recipe.kcal);
    System.out.println(recipe.portionen);
    return recipe;
  }

  public boolean accepts(Recipe recipe) {
    boolean acceptance = false;

    if(recipe.name == null || recipe.zubereitung == null || recipe.zutaten.size()==0)
    {
      return false;
    }
    else{
      return true;
    }
  }

  //Extract the name of a recpipe. Must be in the first non empty row
  //of the textfile
  private String extractRecipename(ArrayList<String> textfileContent){
    String name = "";
    String row = "";

    for(int i=0;i<textfileContent.size();i++){
      row = textfileContent.get(i).trim();
      if(row.length()==0) { continue;}
      else {
        name = row;
        break;
      }
    }
    return name;
  }

  //ExtractIncrededentslist from FIlecontent in two steps
  //Stepp 1: Find the Incedents tagged by "-"
  //Stepp 2: Extract Amount, Unit and Name of each Incredent by using
  //regular expression

  private ArrayList<String[]> extractIncredentsList(ArrayList<String> textfileContent){

    String tempIncredent = null;
    boolean anzVorhanden;
    ArrayList<String> temporaryIingredients = new ArrayList<String>();
    ArrayList<String[]> ingredientList = new ArrayList<String[]>();

    //Step 1: ====================================
    for(int i=0;i<textfileContent.size();i++){
      tempIncredent = textfileContent.get(i).trim();
      if(tempIncredent.startsWith("-")) {
        temporaryIingredients.add(tempIncredent.replaceFirst("-","").trim());
        //checking next line to stop incredents-parsing
        //to prevent wrong incredents in the rest of the recipe
        if(((textfileContent.get(i+1).trim().startsWith("-")==false)
           &&  textfileContent.get(i+1).trim().length()>0 ))
          { break;}
      }
    }

    //Step2: ======================================
    for(int i=0;i<temporaryIingredients.size();i++){
      String zutatElement[] = temporaryIingredients.get(i).split(" ");

      String quanti[];
      quanti = extractAmountUnit(zutatElement[0]);

      if (quanti[0] == null){ anzVorhanden = false; }
      else                  { anzVorhanden = true; }

      if (zutatElement.length==1 && anzVorhanden==false){
        //String Zutat[] = {amount,unit,ingredient};
        String Zutat[] = {null,null,zutatElement[0]};
        ingredientList.add(Zutat);
      }

      else if (zutatElement.length>=2 && anzVorhanden==false){
        String tempStr = buildStringFromMultipleArrays(zutatElement,0);
        String Zutat[] = {quanti[0],quanti[1],tempStr};
        ingredientList.add(Zutat);
      }

      else if (zutatElement.length==2 && anzVorhanden==true){
        String Zutat[] = {quanti[0],quanti[1],zutatElement[1]};
        ingredientList.add(Zutat);
      }

      else if (zutatElement.length==3 && anzVorhanden==true){
        String Zutat[] = {quanti[0],zutatElement[1],zutatElement[2]};
        ingredientList.add(Zutat);
      }

      else if (zutatElement.length>3 && anzVorhanden==true){
        String tempStr = buildStringFromMultipleArrays(zutatElement,2);
        String[] ingredient = {quanti[0],zutatElement[1],tempStr};
        ingredientList.add(ingredient);
      }
    }
    return ingredientList;
   }

  private String findPreparationOfRecipe(ArrayList<String> fileContent)
  {
    boolean lastIngredientFound = false;
    boolean nameFound = false;
    int beginRow = -1;

    String preperation = null;
    String row = "";

    //Find begin of Preperation - ignore Name and Ingredients!
    for(int i=0;i<fileContent.size();i++){

      row = fileContent.get(i).trim();
      if(row.length()==0) {;continue;}
      else if(row.length()>0 && fileContent.get(i).startsWith("-") == false ){
        nameFound = true;
      }

      else if(fileContent.get(i).startsWith("-") == true){
        for (int j=i+1;j<fileContent.size();j++){
          if ((fileContent.get(j).trim().length()==0)){ continue;}
          else if(fileContent.get(j).startsWith("-")==true) { continue; }
          else {
            // Row could be a optional Informationen. Have to be checked
            if (checkRowForSignalwords(fileContent.get(j))==true){
              continue;
            }
            else{

              //Begin of preperation found
              lastIngredientFound = true;
              beginRow = j;
            break;
            }
          }
        }
        if (beginRow > 0 && lastIngredientFound==true ) { break; }
      }
    }
    //Find last row of preperation
    int endRow = findNextSignalword(beginRow,fileContent,"Zubereitung");

//extract preperation from file - if begin < 0 zero there is now preperation
    if (beginRow > 0) {
      for (int i = beginRow; i<endRow;i++){
        if(preperation==null) {
          preperation=fileContent.get(i);
        }
        else{
          preperation=preperation+" "+fileContent.get(i);
        }
      }
      preperation = preperation.trim();
    }
    return preperation;
  }



  private String findDatafield(ArrayList<String> textDateiInhalt,String signalwort)
  {
    String datafield = null;
    int zeileS = findSignalword(textDateiInhalt,signalwort);

    if(zeileS >= 0)
    {
      datafield = textDateiInhalt.get(zeileS);
      datafield=datafield.replaceFirst(signalwort+":","");
      datafield=datafield.trim();
    }
    else {
      return null;
    }
    return datafield;
  }

  private boolean checkRowForSignalwords(String row){
    for (int k=0;k<signalwoerter.length;k++){
      if(row.contains(signalwoerter[k]+":")){
        return true;
      }
    }
    return false;
  }

  private double parseStringToNumeric(String str) {

    double d=0;
    str = str.replaceAll(",", ".");

    try {
      d = Double.parseDouble(str);
    }
    catch (NumberFormatException | NullPointerException e){
      return 0;
    }
    return d;
  }

  private int findSignalword(ArrayList<String> textDateiInhalt,String signalwort)
  {
    for(int i=0;i<textDateiInhalt.size();i++){
      if(textDateiInhalt.get(i).contains(signalwort+":")) {
        return i;
      }
    }
    return -1;
  }

  private int findNextSignalword(int j,ArrayList<String> textDateiInhalt,String signalwort)
  {
    for (int i=j+1;i<textDateiInhalt.size();i++)
    {
      for (int k=1;k<signalwoerter.length;k++)
      {
        if((textDateiInhalt.get(i).contains(signalwoerter[k]+":")) && signalwoerter[k]+":"!=signalwort)
        {
          return i;
        }
      }
    }
    return textDateiInhalt.size()-1;
  }

  /*Checks a String (From Step 1 in extractIncredentsList()) if it contains
  * a (double-)number. It can be zero, one or more Numbers follows by
  * a dot and followe by zero, one or more numbers */
  private String[] extractAmountUnit(String s)
  {
    String sTemp = s;
    String [] quanti = new String[2];
    quanti[0] = null;quanti[1] = null;

    try
    {
      //repace "," with "." to simplify and to prvent errors
      s=s.replaceFirst(",",".");

      Pattern p = Pattern.compile("[-+]?([0-9]*\\.[0-9]+|[0-9]+)");
      Matcher m = p.matcher(s);

      while (m.find()) { s=s.substring(m.start(), m.end());}

      //If there is not a correct double we will get a exception
      //It dont have to be handled because defaults will be used
      Double.parseDouble(s);

      quanti[0] = s;
      quanti[1] = sTemp.replaceFirst(s, "").trim();
      if (quanti[1].length() == 0 ) {
        quanti[1] = null;
      }

      return quanti;
    }
    catch (Exception e) { return quanti; }
  }

  //This Method is for longer names for Ingredients
  private String buildStringFromMultipleArrays(String[] s,int j){
    String tempStr ="";
    for (int i = j;i<s.length;i++){
      tempStr = tempStr+" "+s[i];
    }
    return tempStr.trim();
  }
}

