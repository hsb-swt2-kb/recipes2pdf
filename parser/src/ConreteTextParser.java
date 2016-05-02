import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ConreteTextParser extends Parser implements Konstanten
{
  public Recipe parse(ArrayList<String> textFileContent) {
    Recipe recipe = new Recipe();
    
    //Extract minimal recipdata
    recipe.name=extractRecipename(textFileContent);
    ArrayList<String[]> incredents = extractIncredentsList(textFileContent);
   System.out.println(recipe.zubereitung = findPreparationOfRecipe(textFileContent));
    
    //extract addtional recipeinformation
    //System.out.println(recipe.kategorie = findDatafield(textFileContent,"Kategorie"));
    
    
    return recipe;
  }
  
  public boolean accepts() {
    boolean acceptance = false;
    
    
    
    
    return acceptance;
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
    boolean ignoreText = true;
    int beginRow = -1;
   
    String preperation = null;
    String row = "";
    
    //Find begin of Preperation - ignore Name and Ingredients!
    for(int i=0;i<fileContent.size();i++){
      row = fileContent.get(i).trim();
      if(row.length()==0 || ignoreText == true) { continue;}
      
      else if(fileContent.get(i).trim().startsWith("-")){
        for (int j=i+1;j<fileContent.size();j++){
          if (row.trim().length()==0) { continue;}
          else if(fileContent.get(j).trim().startsWith("-")==true) { break;}
          else {
            ignoreText = false;
            //Begin of preperation found
            beginRow = j;
            break;
          }
        }
        if (beginRow > 0) { break; }
      }      
    }
    //Find last row of preperation
    int endRow = findNextSignalword(beginRow,fileContent,"Zubereitung");
    System.out.println(beginRow+" "+endRow);
//extract preperation from file - if begin < 0 zero there is now preperation
    if (beginRow > 0) {
      for (int i = beginRow; i<endRow;i++){
        if(preperation==null) {
          preperation=fileContent.get(i);
        }
        else{
          preperation=preperation+fileContent.get(i);
        } 
      }
      preperation = preperation.trim();
    }
    return preperation;
  }
	

  
  public String findDatafield(ArrayList<String> textDateiInhalt,String signalwort)
  {
    String Daten = null;
    int zeileS = findSignalword(textDateiInhalt,signalwort);
    
    if(zeileS >= 0)
    {
      int nZeile = findNextSignalword(zeileS,textDateiInhalt,signalwort);

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
  
  public int findSignalword(ArrayList<String> textDateiInhalt,String signalwort)
  {
    for(int i=0;i<textDateiInhalt.size();i++){
      if(textDateiInhalt.get(i).contains(signalwort+":")) {
        return i;
      }
    }
    return -1;
  }
    
  public int findNextSignalword(int j,ArrayList<String> textDateiInhalt,String signalwort)
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
  public String[] extractAmountUnit(String s)
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
      quanti[1] = sTemp.replaceFirst(s, null);
      
      return quanti;
    }
    catch (Exception e) { return quanti; }
  }
  
  private String buildStringFromMultipleArrays(String[] s,int j){
    String tempStr ="";
    for (int i = j;i<s.length;i++){
      tempStr = tempStr+" "+s[i];
    }
    return tempStr.trim();
  }
 
  }  

