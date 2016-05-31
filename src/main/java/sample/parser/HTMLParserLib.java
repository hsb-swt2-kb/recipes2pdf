package sample.parser;

import sample.model.Recipe;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fpfennig on 29.05.2016.
 */
public class HTMLParserLib {
    public static final String numberWithCharacters    = "^(\\d)+([.,]\\d)*([äüöÄÖÜ\\w])*";
    public static final String charactersWithoutNumbers = "([^.,\\d])+";
    public static final int    fractionalDigits        = 3;
    public static final int    maxFieldsize            = 45;
    public static final String numberSeperator         = "[.,]";

    /**
     * Method to download the image of an recipe.
     *
     * @param image String containing the url to the image.
     * @return the downloaded image as Byte Array.
     */
    public byte[] downloadImage(String image){
        byte[] result = null;

        try {
            URL url = new URL(image);
            InputStream input = new BufferedInputStream(url.openStream());
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int next = 0;
            while ((next = input.read(buf)) != -1) {
                output.write(buf, 0, next);
            }
            output.close();
            input.close();
            result = output.toByteArray();
        }
        catch(Exception e){
            // TODO: Exception handling
            //e.printStackTrace();
        }

        return result;
    }

    /**
     * Method to remove unnecessary decimal seperators and change comma to point.
     *
     * @param number The String which contains a number.
     * @return Number with only one decimal point.
     */
    public String replaceDecimalSeperators(String number){
        String result = "";
        Pattern regexPattern = Pattern.compile(numberSeperator);
        Matcher regexMatcher = regexPattern.matcher(number);

        if(regexMatcher.find()){
            String[] seperate = number.split(numberSeperator);
            boolean firstSperator = true;

            for (int counter = 0; counter < seperate.length; counter++){

                if(!seperate[counter].isEmpty()){
                    result = result + seperate[counter];
                }

                if(firstSperator){
                    if(counter == 0 && seperate[counter].isEmpty()){
                        result = result + "0";
                    }
                    result = result + ".";

                    firstSperator = false;
                }
            }
        }
        else{
            result = number;
        }

        return result;
    }

    /**
     * Converting the ingredient ArrayList into the needed ArrayList.
     *
     * @param ingredientList ArrayList of Strings
     * @return Converted IngredientList, ArrayList of String Arrays
     */
    public ArrayList<String[]> convertIngredientList(ArrayList<String> ingredientList){
        ArrayList<String[]> result = new ArrayList<>();

        for (String listEntry : ingredientList) {
            String[] filtering = listEntry.split(" ");
            String filteredString = "";

            if(filtering.length > 1) {
                // Removing extra numeral characters
                Pattern filterRegex = Pattern.compile(numberWithCharacters);
                Matcher filterMatcher = filterRegex.matcher(filtering[0]);
                Matcher filterMatcher2 = filterRegex.matcher(filtering[1]);

                if (filterMatcher.find() && filterMatcher2.find()) {
                    for (int counter = 1; counter < filtering.length; counter++) {
                        filteredString = filteredString + " " + filtering[counter];
                    }
                }
                else {
                    filteredString = listEntry;
                }
            }
            else {
                filteredString = listEntry;
            }

            String[] workingArray = filteredString.trim().split(" ");
            String amount = "";
            String unit = "";
            String ingredient = "";

            if(workingArray[0].matches(numberWithCharacters)){
                // Characters need to be removed from the amount, and will be used as unit.
                // The whole String will be used as amount, if there are no characters.
                Pattern regexPattern = Pattern.compile(charactersWithoutNumbers);
                Matcher regexMatcher = regexPattern.matcher(workingArray[0]);

                if(regexMatcher.find()){
                    // Search for the index of the characters IN the amount String.
                    int index = regexMatcher.start();
                    String amountRaw = workingArray[0].substring(0,index);

                    amount = replaceDecimalSeperators(amountRaw);

                    // The characters in the amount String are the unit.
                    unit = workingArray[0].substring(index);
                }
                else {
                    amount = replaceDecimalSeperators(workingArray[0]);
                }
            }

            // Looking for the unit if it was not in the amount AND if the array has more than 2 entries.
            // If this is not the case, the unit will be set as null, and the rest String will be the ingredient.
            if(unit.isEmpty() && workingArray.length > 2){
                unit = null;

                if(workingArray[0].matches(numberWithCharacters)){
                    unit = workingArray[1];

                    for(int counter = 2; counter < workingArray.length; counter++){
                        if(counter == 2){
                            ingredient = ingredient + workingArray[counter];
                        }
                        else{
                            ingredient = ingredient + " " + workingArray[counter];
                        }
                    }
                }
                else{
                    for (int counter = 0; counter < workingArray.length; counter++) {
                        if (counter == 0) {
                            ingredient = ingredient + workingArray[counter];
                        } else {
                            ingredient = ingredient + " " + workingArray[counter];
                        }
                    }
                }
            }
            else if(!unit.isEmpty()){
                for(int counter = 1; counter < workingArray.length; counter++){
                    if(counter == 1){
                        ingredient = ingredient + workingArray[counter];
                    }
                    else{
                        ingredient = ingredient + " " + workingArray[counter];
                    }
                }
            }
            else{
                unit = null;

                if(workingArray[0].matches(numberWithCharacters)){
                    for (int counter = 1; counter < workingArray.length; counter++) {
                        if (counter == 1) {
                            ingredient = ingredient + workingArray[counter];
                        } else {
                            ingredient = ingredient + " " + workingArray[counter];
                        }
                    }
                }
                else{
                    for (int counter = 0; counter < workingArray.length; counter++) {
                        if (counter == 0) {
                            ingredient = ingredient + workingArray[counter];
                        } else {
                            ingredient = ingredient + " " + workingArray[counter];
                        }
                    }

                }
            }

            if(amount.isEmpty()){
                String work = "";
                if(unit != null){
                    work = work + unit + " ";
                    unit = null;
                }
                work = work + ingredient;
                ingredient = work.trim();
            }

            // Collecting needed Data into one array
            String[] ingredientArray = new String[3];
            if(amount.length() > maxFieldsize){
                ingredientArray[0] = amount.substring(0, maxFieldsize);
            }
            else {
                ingredientArray[0] = amount;
            }

            if(unit != null && unit.length() > maxFieldsize){
                ingredientArray[1] = unit.substring(0, maxFieldsize);
            }
            else{
                ingredientArray[1] = unit;
            }

            if(ingredient.length() > maxFieldsize){
                ingredientArray[2] = ingredient.substring(0, maxFieldsize);
            }
            else{
                ingredientArray[2] = ingredient;
            }

            result.add(ingredientArray);
        }

        return result;
    }

    /**
     * Method to round the double values.
     *
     * @param amount
     * @return
     */
    public double round(double amount){
        double rounding = Math.pow(10, fractionalDigits);

        return (double)Math.round(amount * rounding) / rounding;
    }

    /**
     * Method to check all fields of the recipe.
     *
     * @return true if all mandatory fields are set, false if not.
     */
    public boolean checkRecipe(Recipe recipe){
        return !recipe.isIncomplete();
    }
}
