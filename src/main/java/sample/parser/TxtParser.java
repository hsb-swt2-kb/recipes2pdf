package sample.parser;

import sample.model.*;

import java.lang.Math;
import java.lang.StringBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* This Class can extract information for a recipe from a textFile

  precondition: A ArrayList of String, that contains information from a textFile
  each row in the ArrayList is one row in the textFile

  After extracting all available information, the recipe will be checked for
  minimal data like recipeName, ingredientList with at least one ingredient
  and a preparation text.

  All additional data like region of the recipe (ex. Greece, France, Africans etc..)
  have to be declared in the textFile like "Region: France"

  Author: Henrik Giessel
  last edited: 3rd May of 2016
*/

class TxtParser extends AConcreteParser implements TxtParserConstants {

    /**
     * @param textFileContent via ArrayList of String, where one Element = one Line from textFile
     * This parse()-method fill a Recipe-Object
     * STEP1: Extract Name: (it has to be in the first non-empty Line)
     * STEP2: Extract ingredientList: (One  ingredient per Line)
     *
     *                        ingredient-Element Example: - 500 g Salt
     *                        [Amount][" "][Unit] Name
     *                        Amount and Unit are optional. Amount is 0.0 (double) per default
     *                                                      Unit is null per default
     * STEP3: Extract Recipe-preparation with findPreparationWithTag. If it do not work because it is null
     *      try it with findPreparationOfRecipe()
     *
     * STEP4: Extract additional Recipe-data with String Format like Region, Course, Nurture,
     *                        Category, Daytime and Season of the Recipe. The specific Data of all optional Fields
     *                        have to be tagged like "Season:" that are stored in the Interface Constants.java
     *                        Non tagged Data will be ignored except it is the preparation
     *
     * STEP5: Extract additional Recipe-data with Int-Format
     *                        If there are no such data there will be a NullPointerException that set the specific data
     *                        to 0 (Integer)
     *                        If there is a NumberFormatException we try to fix it by extracting number with
     *                        extractAmountUnit().
     *
     * @return recipe
     *
     * NOTE: Higher Instances have to make sure, that a recipe is valid with minimal Options
     */
    public Recipe parse(List<String> textFileContent) {

        //Try to find Name without Tag
        //================STEP1==================
        recipe.setTitle(extractRecipeName(textFileContent));

       //=================STEP2==================
        // Set IngredientList from RecipeObject from temporaryList
        List<String[]> tempIngredientList;
        tempIngredientList = extractIngredientList(textFileContent);

        for (String[] ingredient:tempIngredientList) {
            String tempName   = cutString(ingredient[2],fieldLength);
            String tempUnit   = cutString(ingredient[1],fieldLength);
            String tempAmount = cutString(ingredient[0],fieldLength);

            //         IngredientName, Amount, UnitName
            recipe.add(tempName, parseStringToDouble(tempAmount), tempUnit);
        }
        // FInd PreparationText with tag...If there is now Tag use specific Method
        // ===================STEP3===================================
        String tempPreparation= findPreparationWithTag(textFileContent);

        if (tempPreparation==null){
            recipe.setText(findPreparationOfRecipe(textFileContent));
        }
        else{
            if (tempPreparation.length()>preparationLength){
                tempPreparation = cutString(tempPreparation,preparationLength);
            }
            recipe.setText(tempPreparation);
        }
        // Try to extract additional recipedata
        //===================STEP4===================================
        Category category = new Category();
        category.setName(findDatafield(textFileContent, signalWord[3]));
        recipe.setCategory(category);
        //=============================
        Course course = new Course();
        course.setName(findDatafield(textFileContent, signalWord[5]));
        recipe.setCourse(course);
        //=============================
        Region region = new Region();
        region.setName(findDatafield(textFileContent, signalWord[8]));
        recipe.setRegion(region);
        //=============================
        Daytime daytime = new Daytime();
        daytime.setName(findDatafield(textFileContent, signalWord[9]));
        recipe.setDaytime(daytime);
        //=============================
        Season season = new Season();
        season.setName(findDatafield(textFileContent, signalWord[10]));
        recipe.setSeason(season);
        //=============================
        Nurture nurture = new Nurture();
        nurture.setName(findDatafield(textFileContent, signalWord[11]));
        recipe.setNurture(nurture);

        //===================STEP5=============================
        try {  // TODO: handle null values
            recipe.setDuration(Integer.parseInt(findDatafield(textFileContent, signalWord[4])));
        } catch (NumberFormatException e) {
            String [] str = extractAmountUnit(findDatafield(textFileContent, signalWord[4]));

            if (str[0] == null || str[0].equals("0")) {
                recipe.setDuration(0);
            }
            else {
                double d = parseStringToDouble(str[0]);
                boolean std = false;

                for (String timeHourWord : timeHourWords) {
                    if (str[1].toLowerCase().contains(timeHourWord)) {
                        std = true;
                        break;
                    }
                }
                // convert hour to Minute
                if (std){
                    d = d*60;
                }
                // Cast double to int
                int i = (int) Math.floor(d);
                recipe.setDuration(i);
            }
        }
        catch (NullPointerException e){ recipe.setDuration(0);}
        try {
            recipe.setCalories(Integer.parseInt(findDatafield(textFileContent, signalWord[7])));
        }

        catch (NumberFormatException e) {
            String cal = findDatafield(textFileContent, signalWord[7]);
            recipe.setCalories(fixNumberFormat(cal));
        }
        catch (Exception e){
            recipe.setCalories(0);
        }

        try {
            recipe.setPortions(Integer.parseInt(findDatafield(textFileContent, signalWord[6])));
        }
        catch (NumberFormatException e){
            String port = findDatafield(textFileContent, signalWord[6]);
            recipe.setPortions(fixNumberFormat(port));
        }
        catch (Exception e) {
            recipe.setPortions(0);
        }
        ISource source = new Source();
        source.setName("Eigene");
        recipe.setSource(source);

        return recipe;
    }


    /**
     * @param fileContent List<String> of the recipe content
     * @return boolean: If it is true, the parse() could extract a minimal Recipe. If it is a wrong format like html, it
     * will be false.
     */
    public boolean accepts(List<String> fileContent) {
      /* parse it to check it :) */
        return !this.parse(fileContent).isIncomplete();
    }

  /** A Method to fix a NumberFormatException in parse()
   * @param str String to fix
   * @return int value contained in str
   */
  private int fixNumberFormat(String str){
        String string[] = extractAmountUnit(str);
        double d = parseStringToDouble(string[0]);
        return (int) Math.floor(d);
    }
    //Extract the name of a recipe. Must be in the first non empty row
    //of the textFile
    private String extractRecipeName(List<String> textFileContent) {
        String name,row = "";

        for (String line:textFileContent) {
            row = line.trim();
            if (row.length() > 0)
                break;
        }
        //If the Name-Tag is in String it will be replaced
        name = cutString(row.replaceAll("name:", "").replaceAll(signalWord[0],"").trim(),fieldLength);
        return name;
    }

    /**
     * ExtractIngredientsList from fileContent in two steps
     * Step 1: Find the ingredients tagged by "-"
     * Step 2: Extract Amount, Unit and Name of each ingredient by using
     * regular expression
     *
     * @param textFileContent List<String> content of the recipe file
     * @return ArrayList with
   */
    private ArrayList<String[]> extractIngredientList(List<String> textFileContent) {

        String tempIngredient;
        boolean anzPresent;
        ArrayList<String> temporaryIngredients = new ArrayList<>();
        ArrayList<String[]> ingredientList = new ArrayList<>();

        //Step 1: ====================================
        for (int i = 0; i < textFileContent.size(); i++) {
            tempIngredient = textFileContent.get(i).trim();
            if (tempIngredient.startsWith("-")) {
                temporaryIngredients.add(tempIngredient.replace(ingredientMarker, "").trim());
                //checking next line to stop ingredients-parsing
                //to prevent wrong ingredients in the rest of the recipe
                if (((!textFileContent.get(i + 1).trim().startsWith("-"))
                    && textFileContent.get(i + 1).trim().length() > 0)) {
                    break;
                }
            }
        }

        //Step2: ======================================
        for (String temporaryIngredient : temporaryIngredients) {
            String ingredientElement[] = temporaryIngredient.split(" ");

            String quantity[];
            quantity = extractAmountUnit(ingredientElement[0]);

            anzPresent = quantity[0] != null;

            if (ingredientElement.length == 1 && !anzPresent) {
                String Zutat[] = {null, null, ingredientElement[0]};
                ingredientList.add(Zutat);
            } else if (ingredientElement.length >= 2 && !anzPresent) {
                String tempStr = buildStringFromMultipleArrays(ingredientElement, 0);
                String Zutat[] = {quantity[0], quantity[1], tempStr};
                ingredientList.add(Zutat);
            } else if (ingredientElement.length == 2 && anzPresent) {
                String Zutat[] = {quantity[0], quantity[1], ingredientElement[1]};
                ingredientList.add(Zutat);
            } else if (ingredientElement.length == 3 && anzPresent) {
                String Zutat[] = {quantity[0], ingredientElement[1], ingredientElement[2]};
                ingredientList.add(Zutat);
            } else if (ingredientElement.length > 3 && anzPresent) {
                String tempStr = buildStringFromMultipleArrays(ingredientElement, 2);
                String[] ingredient = {quantity[0], ingredientElement[1], tempStr};
                ingredientList.add(ingredient);
            }
        }
        return ingredientList;
    }

    private String findPreparationOfRecipe(List<String> fileContent) {
        boolean lastIngredientFound = false;
        boolean nameFound = false;
        int beginRow = -1;

        String preparation = null;
        String row = "";

        //Find begin of preparation - ignore Name and Ingredients!
        for (int i = 0; i < fileContent.size(); i++) {

            row = fileContent.get(i).trim();
            if (row.length() > 0 && !fileContent.get(i).startsWith("-")) {
                nameFound = true;
            } else if (fileContent.get(i).startsWith("-")) {
                for (int j = i + 1; j < fileContent.size(); j++) {
                    if (fileContent.get(j).trim().length()>0 && fileContent.get(j).startsWith("-") && checkRowForSignalwords(fileContent.get(j))){
                        //Begin of preparation found
                        lastIngredientFound = true;
                        beginRow = j;
                        break;
                    }
                }
                if (beginRow > 0 && lastIngredientFound == true) {
                    break;
                }
            }
        }

        //Find last row of preparation
        int endRow = findNextSignalWord(beginRow, fileContent, signalWord[2]);

        //extract preparation from file - if begin < 0 zero there is now preparation
        if (beginRow > 0) {
            for (int i = beginRow; i < endRow; i++) {
                if (preparation == null) {
                    preparation = fileContent.get(i);
                } else {
                    preparation = preparation + " " + fileContent.get(i);
                }
            }
            preparation = preparation.trim();
            preparation = cutString(preparation,preparationLength);
        }
        return preparation;
    }

    private String findPreparationWithTag(List<String> textFileContent){
        String tempStr = null;

        int beginRow = findSignalWord(textFileContent,signalWord[2]);
        if (beginRow > 0){
            int endRow = findNextSignalWord(beginRow,textFileContent,signalWord[2]);
            for (int i = beginRow;i<=endRow;i++){

                tempStr = tempStr+textFileContent.get(i);
                try{
                    if (checkRowForSignalwords(textFileContent.get(i + 1))){
                        break;
                    }
                }
                catch(IndexOutOfBoundsException e){
                    break;
                }
            }
            tempStr=tempStr.replaceFirst("null","").trim();
            tempStr=tempStr.replaceFirst(signalWord[2]+ attributeMarker,"").trim();
            return tempStr;
        }
        else
            return tempStr;
    }

    private String findDatafield(List<String> textFileContent, String signalwort) {
        String datafield = null;
        int zeileS = findSignalWord(textFileContent, signalwort);

        if (zeileS >= 0) {
            datafield = textFileContent.get(zeileS);
            datafield = datafield.replaceFirst(signalwort + attributeMarker, "");
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
        for (String aSignalWord : signalWord) {
            if (row.contains(aSignalWord + attributeMarker)) {
                return true;
            }
        }
        return false;
    }

    private double parseStringToDouble(String str) {

        double d;
        try {
            str = str.replaceAll(",", ".");
            int temp = str.lastIndexOf(".");
            int tempLength = str.length();
            StringBuilder build = new StringBuilder(str);
            if(str.contains(".")){
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

    private int findSignalWord(List<String> textFileContent, String signalWord) {
        for (int i = 0; i < textFileContent.size(); i++) {
            if (textFileContent.get(i).contains(signalWord + attributeMarker)) {
                return i;
            }
        }
        return -1;
    }

    private int findNextSignalWord(int j, List<String> textFileContent, String signalWord) {
        for (int i = j + 1; i < textFileContent.size(); i++) {
            for (int k = 1; k < TxtParserConstants.signalWord.length; k++) {
                if ((textFileContent.get(i).contains(TxtParserConstants.signalWord[k] + attributeMarker)) && !Objects.equals(TxtParserConstants.signalWord[k] + attributeMarker, signalWord)) {
                    return i;
                }
            }
        }
        return textFileContent.size() - 1;
    }

    /* Checks a String (From Step 1 in extractIncredentsList()) if it contains
    * a (double-)number. It can be zero, one or more Numbers follows by
    * a dot and followe by zero, one or more numbers */
    private String[] extractAmountUnit(String s) {
        String sTemp = s;
        String[] quanti = new String[2];
        quanti[0] = null;
        quanti[1] = null;

        try {
            //replace "," with "." to simplify and to prevent errors
            s = s.replaceFirst(",", ".");

            Pattern p = Pattern.compile("[-+]?([0-9]*\\.[0-9]+|[0-9]+)");
            Matcher m = p.matcher(s);

            while (m.find())
                s = s.substring(m.start(), m.end());

            //If there is not a correct double we will get a exception
            //It don't have to be handled because defaults will be used
            Double.parseDouble(s);

            quanti[0] = s;
            quanti[1] = sTemp.replaceFirst(s, "").trim();
            if (quanti[1].length() == 0)
                quanti[1] = null;

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
        else if (str.length()>maxLength) {
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

