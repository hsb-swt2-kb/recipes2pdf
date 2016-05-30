package sample.parser;

import sample.model.*;

import java.lang.reflect.Array;
import java.time.Duration;
import java.lang.Math;
import java.lang.StringBuilder;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* This Class can extract information for a recipe from a textfile

  precondition: A Arraylist of String, that contains information from a Textfile
  each row in the Arraxlist is one row in the textfile

  After exctracting all availible information, the recipe will be check for
  minimal data like recipename, Ingredientlist with at least one incredient
  d a preperationtext.

  All additional data like region of the recipe (ex. Greece, France, Africans etc..)
  have to be declared in the Textfile like "Region: France"

  Author: Henrik Giessel
  last edited: 3rd May of 2016
*/


public class TxtParser extends AConcreteParser implements Constants {
    private final int fieldLength = 45;
    private final int preparationLength =4545;

    public Recipe parse(ArrayList<String> textFileContent) {
        Recipe recipe = new Recipe();
        ArrayList<String[]> tempIncredientList = new ArrayList<String[]>();

        //Try to extract minimal recipdata
        //TODO Try to find name-tag
        //Try to find Name without Tag
        recipe.setTitle(extractRecipename(textFileContent));

        //recipe.setNurture()
        // Set IngredientList from RecipeObject from temporaryList
        tempIncredientList = extractIncredentsList(textFileContent);
        for (int i = 0; i < tempIncredientList.size(); i++) {
            String tempName = cutString(tempIncredientList.get(i)[2],fieldLength);
            String tempUnit = cutString(tempIncredientList.get(i)[1],fieldLength);
            String tempAmount = cutString(tempIncredientList.get(i)[0],fieldLength);
            //         IngredientName, Amount, UnitName
            recipe.add(tempName, parseStringToDouble(tempAmount), tempUnit);
        }
        // FInd PreperationText with tag...If there is now Tag use specific Method
        String tempPreperation=findPreperationWithTag(textFileContent);
        if (tempPreperation==null){
            recipe.setText(findPreparationOfRecipe(textFileContent));
        }
        else{
            recipe.setText(tempPreperation);
        }


        // Try to extract additional recipedata
        Region region = new Region();
        region.setName(findDatafield(textFileContent, "Region"));
        recipe.setRegion(region);
        //=============================
        Course course = new Course();
        course.setName(findDatafield(textFileContent, "Gerichtsart"));
        recipe.setCourse(course);
        //=============================
        Category category = new Category();
        category.setName(findDatafield(textFileContent, "Kategorie"));
        recipe.setCategory(category);
        //=============================
        Season season = new Season();
        season.setName(findDatafield(textFileContent, "Saison"));
        recipe.setSeason(season);
        //=============================
        Nurture nurture = new Nurture();
        nurture.setName(findDatafield(textFileContent, "Ernährungsart"));
        recipe.setNurture(nurture);
        //=============================
        Daytime daytime = new Daytime();
        daytime.setName(findDatafield(textFileContent, "Tageszeit"));
        recipe.setDaytime(daytime);

        try {
            recipe.setDuration(Integer.parseInt(findDatafield(textFileContent, "Arbeitszeit")));
        } catch (NumberFormatException | NullPointerException e) {
            recipe.setDuration(0);
        }
        try {
            recipe.setCalories(Integer.parseInt(findDatafield(textFileContent, "Kalorien")));
        } catch (NumberFormatException | NullPointerException e) {
            recipe.setCalories(0);
        }
        try {
            recipe.setPortions(Integer.parseInt(findDatafield(textFileContent, "Portionen")));
        } catch (NumberFormatException | NullPointerException e) {
            recipe.setPortions(0);
        }

        return recipe;
    }

    public boolean accepts(ArrayList<String> fileContent) {
      /* parse it to check it :) */
        Recipe recipe = this.parse(fileContent);
        if (!(recipe.isEmpty() || recipe.isIncomplete()))
            return true;
        else
            return false;
    }

    //Extract the name of a recpipe. Must be in the first non empty row
    //of the textfile
    private String extractRecipename(ArrayList<String> textfileContent) {
        String name = "";
        String row = "";

        for (int i = 0; i < textfileContent.size(); i++) {
            row = textfileContent.get(i).trim();
            if (row.length() == 0) {
                continue;
            } else {
                name = row;
                break;
            }
        }
        name = row.replaceAll("name:", "").trim();
        name = row.replaceAll("Name:", "").trim();
        name = cutString(name,fieldLength);
        return name;
    }

    //ExtractIncrededentslist from FIlecontent in two steps
    //Stepp 1: Find the Incedents tagged by "-"
    //Stepp 2: Extract Amount, Unit and Name of each Incredent by using
    //regular expression

    private ArrayList<String[]> extractIncredentsList(ArrayList<String> textfileContent) {

        String tempIncredent = null;
        boolean anzVorhanden;
        ArrayList<String> temporaryIingredients = new ArrayList<String>();
        ArrayList<String[]> ingredientList = new ArrayList<String[]>();

        //Step 1: ====================================
        for (int i = 0; i < textfileContent.size(); i++) {
            tempIncredent = textfileContent.get(i).trim();
            if (tempIncredent.startsWith("-")) {
                temporaryIingredients.add(tempIncredent.replace("-", "").trim());
                //checking next line to stop ingredients-parsing
                //to prevent wrong ingredients in the rest of the recipe
                if (((textfileContent.get(i + 1).trim().startsWith("-") == false)
                    && textfileContent.get(i + 1).trim().length() > 0)) {
                    break;
                }
            }
        }

        //Step2: ======================================
        for (int i = 0; i < temporaryIingredients.size(); i++) {
            String zutatElement[] = temporaryIingredients.get(i).split(" ");

            String quanti[];
            quanti = extractAmountUnit(zutatElement[0]);

            if (quanti[0] == null) {
                anzVorhanden = false;
            } else {
                anzVorhanden = true;
            }

            if (zutatElement.length == 1 && anzVorhanden == false) {
                //String Zutat[] = {amount,unit,ingredient};
                String Zutat[] = {null, null, zutatElement[0]};
                ingredientList.add(Zutat);
            } else if (zutatElement.length >= 2 && anzVorhanden == false) {
                String tempStr = buildStringFromMultipleArrays(zutatElement, 0);
                String Zutat[] = {quanti[0], quanti[1], tempStr};
                ingredientList.add(Zutat);
            } else if (zutatElement.length == 2 && anzVorhanden == true) {
                String Zutat[] = {quanti[0], quanti[1], zutatElement[1]};
                ingredientList.add(Zutat);
            } else if (zutatElement.length == 3 && anzVorhanden == true) {
                String Zutat[] = {quanti[0], zutatElement[1], zutatElement[2]};
                ingredientList.add(Zutat);
            } else if (zutatElement.length > 3 && anzVorhanden == true) {
                String tempStr = buildStringFromMultipleArrays(zutatElement, 2);
                String[] ingredient = {quanti[0], zutatElement[1], tempStr};
                ingredientList.add(ingredient);
            }
        }
        return ingredientList;
    }

    private String findPreparationOfRecipe(ArrayList<String> fileContent) {
        boolean lastIngredientFound = false;
        boolean nameFound = false;
        int beginRow = -1;

        String preperation = null;
        String row = "";

        //Find begin of Preperation - ignore Name and Ingredients!
        for (int i = 0; i < fileContent.size(); i++) {

            row = fileContent.get(i).trim();
            if (row.length() == 0) {
                continue;
            } else if (row.length() > 0 && fileContent.get(i).startsWith("-") == false) {
                nameFound = true;
            } else if (fileContent.get(i).startsWith("-") == true) {
                for (int j = i + 1; j < fileContent.size(); j++) {
                    if ((fileContent.get(j).trim().length() == 0)) {
                        continue;
                    } else if (fileContent.get(j).startsWith("-") == true) {
                        continue;
                    } else {
                        // Row could be a optional Informationen. Have to be checked
                        if (checkRowForSignalwords(fileContent.get(j)) == true) {
                            continue;
                        } else {

                            //Begin of preperation found
                            lastIngredientFound = true;
                            beginRow = j;
                            break;
                        }
                    }
                }
                if (beginRow > 0 && lastIngredientFound == true) {
                    break;
                }
            }
        }

        //Find last row of preperation
        int endRow = findNextSignalword(beginRow, fileContent, "Zubereitung");

//extract preperation from file - if begin < 0 zero there is now preperation
        if (beginRow > 0) {
            for (int i = beginRow; i < endRow; i++) {
                if (preperation == null) {
                    preperation = fileContent.get(i);
                } else {
                    preperation = preperation + " " + fileContent.get(i);
                }
            }
            preperation = preperation.trim();
            preperation = cutString(preperation,preparationLength);
        }
        return preperation;
    }

    private String findPreperationWithTag(ArrayList<String> textFileContent){
        String tempStr = null;

        int beginRow = findSignalword(textFileContent,"Zubereitung");
        if (beginRow > 0){
            int endRow = findNextSignalword(beginRow,textFileContent,"Zubereitung");
            for (int i = beginRow;i<=endRow;i++){

                tempStr = tempStr+" "+textFileContent.get(i);
                try{
                    if (checkRowForSignalwords(textFileContent.get(i+1)) == true){
                        break;
                    }
                }
                catch(IndexOutOfBoundsException e){
                    break;
                }
            }
            tempStr=tempStr.replaceFirst("null","").trim();
            tempStr=tempStr.replaceFirst("Zubereitung:","").trim();
            return tempStr;
        }
        else
            return tempStr;
    }

    private String findDatafield(ArrayList<String> textFileContent, String signalwort) {
        String datafield = null;
        int zeileS = findSignalword(textFileContent, signalwort);

        if (zeileS >= 0) {
            datafield = textFileContent.get(zeileS);
            datafield = datafield.replaceFirst(signalwort + ":", "");
            datafield = datafield.trim();
            datafield = cutString(datafield,fieldLength);
            if (datafield.length() == 0) {
                datafield = null;
            }
        } else {
            return null;
        }
        return datafield;
    }

    private boolean checkRowForSignalwords(String row) {
        for (int k = 0; k < signalwoerter.length; k++) {
            if (row.contains(signalwoerter[k] + ":")) {
                return true;
            }
        }
        return false;
    }

    private double parseStringToDouble(String str) {

        double d = 0;

        try {
            str = str.replaceAll(",", ".");
            int temp = str.lastIndexOf(".");
            int tempLength = str.length();
            StringBuilder build = new StringBuilder(str);
            if(str.contains(".")==true){
                for (int i=tempLength-1;i>temp+3;i--){
                    build.deleteCharAt(i);
                    str = build.toString();
                }
            }
            d = Double.parseDouble(str);

        } catch (NumberFormatException | NullPointerException e) {
            d = 0;
            return d;
        }
        return d;
    }

    private int findSignalword(ArrayList<String> textDateiInhalt, String signalwort) {
        for (int i = 0; i < textDateiInhalt.size(); i++) {
            if (textDateiInhalt.get(i).contains(signalwort + ":")) {
                return i;
            }
        }
        return -1;
    }

    private int findNextSignalword(int j, ArrayList<String> textDateiInhalt, String signalwort) {
        for (int i = j + 1; i < textDateiInhalt.size(); i++) {
            for (int k = 1; k < signalwoerter.length; k++) {
                if ((textDateiInhalt.get(i).contains(signalwoerter[k] + ":")) && signalwoerter[k] + ":" != signalwort) {
                    return i;
                }
            }
        }
        return textDateiInhalt.size() - 1;
    }

    /*Checks a String (From Step 1 in extractIncredentsList()) if it contains
    * a (double-)number. It can be zero, one or more Numbers follows by
    * a dot and followe by zero, one or more numbers */
    private String[] extractAmountUnit(String s) {
        String sTemp = s;
        String[] quanti = new String[2];
        quanti[0] = null;
        quanti[1] = null;

        try {
            //repace "," with "." to simplify and to prvent errors
            s = s.replaceFirst(",", ".");

            Pattern p = Pattern.compile("[-+]?([0-9]*\\.[0-9]+|[0-9]+)");
            Matcher m = p.matcher(s);

            while (m.find()) {
                s = s.substring(m.start(), m.end());
            }

            //If there is not a correct double we will get a exception
            //It dont have to be handled because defaults will be used
            Double.parseDouble(s);

            quanti[0] = s;
            quanti[1] = sTemp.replaceFirst(s, "").trim();
            if (quanti[1].length() == 0) {
                quanti[1] = null;
            }

            return quanti;
        } catch (Exception e) {
            return quanti;
        }
    }

    //This Method is for longer names for Ingredients
    private String buildStringFromMultipleArrays(String[] s, int j) {
        String tempStr = "";
        for (int i = j; i < s.length; i++) {
            tempStr = tempStr + " " + s[i];
        }
        return tempStr.trim();
    }

    private String cutString(String str, int maxLength){
        if (str == null) {return null;}
        if (str != null && str.length()>maxLength) {
            int tempLength = str.length();
            String tempStr = str;
            StringBuilder build = new StringBuilder(str);
            for (int i = tempLength-1; i >= maxLength; i--) {
                build.deleteCharAt(i);
                tempStr = build.toString();
            }
            return tempStr;
        }
        return str;
    }
}

