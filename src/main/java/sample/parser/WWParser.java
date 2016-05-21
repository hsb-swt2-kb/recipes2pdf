package sample.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import sample.model.Recipe;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Kinith on 18.05.2016.
 */
public class WWParser extends AConcreteParser implements WWConstants{
    private Recipe recipe;

    /**
     * The parse method is used to parse the weight watchers HTML recipe.
     * For that it first decides the version of the document, and then chooses the right methods.
     *
     * @param text The recipe content as text
     * @return Recipe The populated Recipe
     * @throws Exception
     */
    //@Override
    public Recipe parse(final String text) throws Exception {
        Document htmlDoc = Jsoup.parse(text);

        int decision = this.decideVersion(htmlDoc);

        switch (decision){
            case 0:
                this.parseVersion2015(htmlDoc);
                break;
            case 1:
                this.parseVersion2016(htmlDoc);
                break;
            case -1:
            default:
                break;
        }

        //Checking Data will be deletet later
        System.out.println("Name: " + recipe.getTitle());
        System.out.println("Region: " + recipe.getRegion());
        System.out.println("Gerichtsart: " + recipe.getCourse());
        System.out.println("Kategorie: " + recipe.getCalories());
        System.out.println("Arbeitszeit: " + recipe.getDuration());
        System.out.println("Kcal: " + recipe.getCalories());
        System.out.println("Portionen: " + recipe.getPortions());
        for (int i=0;i<recipe.zutaten.size();i++){
            System.out.println("Name: " + recipe.zutaten.get(i)[0] +"   |   "+recipe.zutaten.get(i)[1]+"   |   "+recipe.zutaten.get(i)[2]);
        }
        System.out.println("Zubereitung: " + recipe.zubereitung);

        // Mandatory fields, which need to be set
        if(recipe.name.isEmpty() || recipe.zutaten.isEmpty() || recipe.zubereitung.isEmpty()){
            //TODO
            throw new Exception();
        }

        return recipe;
    }

    /**
     * The parser accepts recipes with <code>weightwatchers</code>(case insensitive) in the recipe text.
     * Improvement note: It might be specified by scanning the html title attribute to avoid false positives.
     *
     * @param text The recipe as text
     * @return true if accepts
     */
    //@Override
    public boolean accepts(final String text) {
        return text.contains("weightwatchers");
    }

    /**
     * The parse method for the 2015 version of the recipes.
     *
     * @param htmlDoc The parsed recipe as a JSoup Document
     * @return void
     */
    private void parseVersion2015(Document htmlDoc){
        Elements elements = htmlDoc.getAllElements();

        recipe.name = this.searchName(elements, WWConstants.name2015);
        ArrayList<String> images = this.searchImage(elements, WWConstants.image2015);
        // TODO
        // IMAGES

        recipe.arbeitszeit = this.searchPreparingTime2015(elements, WWConstants.preparingTime2015);

        int servings = this.searchServings(elements);
        if(servings > 0){
            recipe.portionen = servings;
        }

        recipe.gerichtsart = this.searchType2015(elements, WWConstants.type2015);

        ArrayList<String> ingredientsListToConvert = this.searchIngredients2015(elements, WWConstants.ingredientsAndDescr2015);
        recipe.zutaten = convertIngredientList(ingredientsListToConvert);

        recipe.zubereitung = this.searchDescription2015(elements, WWConstants.ingredientsAndDescr2015);
    }

    /**
     * The parse method for the 2016 version of the recipes.
     *
     * @param htmlDoc The parsed recipe as a JSoup Document
     * @return void
     */
    private void parseVersion2016(Document htmlDoc){
        Elements elements = htmlDoc.select(WWConstants.htmlDoc2016);

        recipe.name = this.searchName(elements, WWConstants.name2016);
        ArrayList<String> images = this.searchImage(elements, WWConstants.image2016);
        // TODO
        // IMAGES

        int servings = this.searchServings(elements);
        if(servings > 0){
            recipe.portionen = servings;
        }

        recipe.gerichtsart = this.searchType(elements);

        ArrayList<String> ingredientsListToConvert = this.searchIngredients2016(elements, WWConstants.ingredients2016);
        recipe.zutaten = convertIngredientList(ingredientsListToConvert);

        recipe.zubereitung = this.searchDescription2016(elements, WWConstants.tableTd);
    }

    /**
     * The method which decides on the version using keywords.
     *
     * @param htmlDoc The parsed recipe as a JSoup Document
     * @return 0 for 2015 version, 1 for 2016 version and -1 for neither
     */
    private int decideVersion(Document htmlDoc){
        Elements htmlElements = htmlDoc.select(WWConstants.inputTag);

        for (Element element : htmlElements) {
            if (element.attr(WWConstants.nameAttr).equals(WWConstants.version2015Name)){
                if (element.attr(WWConstants.valueAttr).equals(WWConstants.version2015)){
                    return 0;
                }
            }
        }

        htmlElements = htmlDoc.select(WWConstants.scriptTag);

        for (Element element: htmlElements) {
            if (element.toString().contains(WWConstants.version2016)){
                return 1;
            }
        }

        return -1;
    }

    /**
     * Search for the type of meal.
     *
     * @param htmlDoc The parsed recipe as a JSoup Document
     * @param searchString The key to search for
     * @return The type of meal
     */
    private String searchType2015(Elements htmlDoc, String searchString){
        Elements elements = htmlDoc.select(searchString);

        return this.searchType(elements);
    }

    /**
     * Search for the type of meal.
     *
     * @param htmlElements The JSoup Document
     * @return The type of meal
     */
    private String searchType(Elements htmlElements){
        String result = "";

        for (Element htmlElement: htmlElements) {
            for (String keyword: WWConstants.keywords) {
                if (htmlElement.text().contains(keyword)) {
                    if (!result.isEmpty()) {
                        result = result + WWConstants.separator;
                    }
                    result = result + keyword;

                    if (keyword.equalsIgnoreCase(WWConstants.noon) || keyword.equalsIgnoreCase(WWConstants.evening)){
                        result = result + WWConstants.meal;
                    }
                }
            }
        }

        return result;
    }

    /**
     * Search for the description of the recipe. The description is split in multiple parts, which are scattered over
     * multiple paragraphs. So for every part needs to be checked if one or more paragraphs are used.
     *
     * @param htmlDoc The parsed recipe as a JSoup Document
     * @param searchString The key to search for
     * @return The description of the recipe
     */
    private String searchDescription2015(Elements htmlDoc, String searchString){
        String result = "";
        Elements htmlElements = htmlDoc.select(searchString);

        for (Element htmlElement: htmlElements) {
            Elements innerHtmlElements = htmlElement.select(WWConstants.listTag);

            for (Element innerHtmlElement: innerHtmlElements) {
                if (innerHtmlElement.hasText() && !htmlElement.text().replaceAll(WWConstants.replaceSpaces," ").trim().isEmpty()) {
                    if (!result.isEmpty()) {
                        result = result + " ";
                    }
                    result = result + innerHtmlElement.text().trim();
                }
            }
        }

        return result;
    }

    /**
     * Search for the description of the recipe. The description is split in multiple parts, which are scattered over
     * multiple paragraphs. So for every part needs to be checked if one or more paragraphs are used.
     *
     * @param htmlDoc The parsed recipe as a JSoup Document
     * @param searchString The key to search for
     * @return The description of the recipe
     */
    private String searchDescription2016(Elements htmlDoc, String searchString){
        String result = "";
        Elements elements = htmlDoc.select(searchString);

        for (Element element: elements){
            if (element.text().contains(WWConstants.preparingTag)) {
                int index = element.text().indexOf(WWConstants.preparingTag);
                String workingString = element.text().substring(index);
                workingString = workingString.replaceAll(WWConstants.replaceSpaces," ").trim();
                ArrayList<String> working = new ArrayList<>(Arrays.asList(workingString.split(" ")));

                // removing empty entries
                for (int counter = 0; counter < working.size(); counter++) {
                    if (!working.get(counter).isEmpty()) {
                        if (working.get(counter).trim().matches(WWConstants.numberWithCharacters)) {
                            if (working.get(counter + 1).trim().matches(WWConstants.numberWithCharacters)) {
                                working.remove(counter);
                            }
                        }
                    }
                }

                // Starting at "1" to ignore the "Zubereitung" entry
                for(int counter = 1; counter < working.size(); counter++){
                    if (!working.get(counter).isEmpty()){
                        if (counter > 1){
                            result = result + " ";
                        }
                        result = result + working.get(counter);
                    }
                }
            }
        }

        return result;
    }

    /**
     * Searching for ingredients entries in the 2015 version.
     *
     * @param htmlDoc
     * @param searchString
     * @return
     */
    private ArrayList<String> searchIngredients2015(Elements htmlDoc, String searchString){
        Elements elements = htmlDoc.select(searchString);
        elements = elements.select(WWConstants.tableTd);
        ArrayList<String> result = new ArrayList<>();

        for (Element element: elements){
            if (element.hasText() && !element.text().replaceAll(WWConstants.replaceSpaces," ").trim().isEmpty()) {
                result.add(element.text().replaceAll(WWConstants.replaceSpaces, " ").trim());
            }
        }

        return result;
    }

    /**
     * Searching for ingredients entries in the 2016 version.
     *
     * @param htmlDoc
     * @param searchString
     * @return
     */
    private ArrayList<String> searchIngredients2016(Elements htmlDoc, String searchString){
        ArrayList<String> result = new ArrayList<>();
        Elements elements = htmlDoc.select(searchString);

        for (Element element: elements){
            if (element.text().contains(WWConstants.ingredientsTag)) {
                String workingString = element.text().replaceAll(WWConstants.replaceSpaces," ").trim();
                String[] working = workingString.split(" ");
                ArrayList<String> list = new ArrayList<>();

                // Putting the splitted results into the list, with an empty entry filter
                for (String splittedString : working) {
                    if (!splittedString.isEmpty()) {
                        list.add(splittedString);
                    }
                }

                // Remove empty entries
                for (int counter = 0; counter < list.size(); counter++) {
                    if (!list.get(counter).isEmpty()) {
                        if (list.get(counter).trim().matches(WWConstants.numberWithCharacters)) {
                            if (list.get(counter + 1).trim().matches(WWConstants.numberWithCharacters)) {
                                list.remove(counter);
                            }
                        }
                    }
                }

                boolean firstTime = true; // Ignoring "Zutaten" entry
                String buildResult = "";

                for (String entry : list) {
                    if (!entry.isEmpty()) {
                        if (!firstTime) {
                            if(entry.trim().matches(WWConstants.numberWithCharacters)) {
                                if (!buildResult.equals("")) {
                                    result.add(buildResult);
                                    buildResult = "";
                                }
                            }
                            else {
                                if (!buildResult.equals("")) {
                                    buildResult = buildResult + " ";
                                }
                            }
                            buildResult = buildResult + entry;
                        }
                        else {
                            firstTime = false;
                        }
                    }
                }

                if (!buildResult.equals("")) {
                    result.add(buildResult);
                }
            }
        }

        return result;
    }

    /**
     * Converting the ingredient ArrayList into the needed ArrayList.
     *
     * @param ingredientList ArrayList of Strings
     * @return Converted IngredientList, ArrayList of String Arrays
     */
    private ArrayList<String[]> convertIngredientList(ArrayList<String> ingredientList){
        ArrayList<String[]> result = new ArrayList<>();

        for (String listEntry : ingredientList) {
            String[] workingArray = listEntry.split(" ");
            String amount = "";
            String unit = "";
            String ingredient = "";

            if(workingArray[0].matches(WWConstants.numberWithCharacters)){
                // Characters need to be removed from the amount, and will be used as unit.
                // The whole String will be used as amount, if there are no characters.
                if(workingArray[0].contains(WWConstants.charactersWithoutNumbers)){
                    // Search for the index of the characters IN the amount String.
                    int index = workingArray[0].indexOf(WWConstants.charactersWithoutNumbers);
                    String amountRaw = workingArray[0].substring(0,index);

                    amount = replaceDecimalSeperators(amountRaw);

                    // The characters in the amount String are the unit.
                    unit = workingArray[0].substring(index);
                }
                else {
                    amount = workingArray[0];
                }
            }

            // Looking for the unit if it was not in the amount AND if the array has more than 2 entries.
            // If this is not the case, the unit will be set as null, and the rest String will be the ingredient.
            if(unit.isEmpty() && workingArray.length > 2){
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
                unit = null;

                for(int counter = 1; counter < workingArray.length; counter++){
                    if(counter == 1){
                        ingredient = ingredient + workingArray[counter];
                    }
                    else{
                        ingredient = ingredient + " " + workingArray[counter];
                    }
                }
            }

            // Collecting needed Data into one array
            String[] ingredientArray = new String[3];
            ingredientArray[0] = amount;
            ingredientArray[1] = unit;
            ingredientArray[2] = ingredient;

            result.add(ingredientArray);
        }

        return result;
    }

    /**
     * Method to remove unnecessary decimal seperators and change comma to point.
     *
     * @param number The String which contains a number.
     * @return Number with only one decimal point.
     */
    private String replaceDecimalSeperators(String number){
        String result = "";

        if(number.contains(WWConstants.numberSeperator)){
            String[] seperate = number.split(WWConstants.numberSeperator);
            boolean firstSperator = true;

            for (int counter = 0; counter < seperate.length; counter++){
                if(firstSperator && seperate[counter].isEmpty()){
                    if(counter == 0){
                        result = result + "0";
                    }
                    result = result + ".";
                }

                if(!seperate[counter].isEmpty()){
                    result = result + seperate[counter];
                }
            }
        }
        else{
            result = number;
        }

        return result;
    }

    /**
     * Filtering the html-document for the preparing time of the meal.
     *
     * @param htmlDoc
     * @param searchString
     * @return
     */
    private String searchPreparingTime2015(Elements htmlDoc, String searchString){
        String result = "";
        Elements elements = htmlDoc.select(searchString);
        // Description needs to be filtered
        boolean firstTime = true;

        for (Element element : elements) {
            // Protected spaces needs to be filtered
            String[] splits = element.text().replaceAll(WWConstants.replaceSpaces, " ").trim().split(" ");
            for (String split : splits) {
                if(!firstTime) {
                    if (!split.trim().isEmpty()) {
                        if (!result.isEmpty()) {
                            result = result + " ";
                        }
                        result = result + split.trim();
                    }
                }
                else {
                    firstTime = false;
                }
            }
        }

        return result;
    }

    /**
     * Filtering the html-document for the number of servings, returns 0 if no valid entry was found.
     *
     * @param htmlDoc
     * @return
     */
    private int searchServings(Elements htmlDoc){
        int result = 0;
        String working = "";
        String text = htmlDoc.text().replaceAll(WWConstants.replaceSpaces, " ").trim();

        // "Portionen: 1" or "Portionen: 10" etc.
        if (text.contains(WWConstants.servingsTag1)){
            int index = text.indexOf(WWConstants.servingsTag1);
            String substring = text.substring(index);
            String[] servings = substring.trim().split(" ");
            working = servings[1].trim();
        }
        // "(Für 1 Person)" or "(Für 10 Personen)" etc.
        else if (text.contains(WWConstants.servingsTag2)){
            int index = text.indexOf(WWConstants.servingsTag2);
            String substring = text.substring(index - 3);
            String[] servings = substring.trim().split(" ");
            working = servings[0].trim();
        }

        // Removing non numeral characters
        if(working.contains(WWConstants.notANumber)){
            working.replaceAll(WWConstants.notANumber, "");
        }

        if(!working.isEmpty() && working.matches(WWConstants.onlyNumber)){
            try {
                result = Integer.getInteger(working);
            }
            catch(NumberFormatException nfe){
                result = 0;
            }
        }

        return result;
    }

    /**
     * Filtering the html-document for images, returns a list of ALL images found.
     *
     * @param htmlDoc
     * @param searchString
     * @return
     */
    private ArrayList<String> searchImage(Elements htmlDoc, String searchString){
        Elements elements = htmlDoc.select(searchString).select(WWConstants.imgTag);
        ArrayList<String> result = new ArrayList<>();

        for (Element element : elements) {
            result.add(element.attr(WWConstants.srcAttr).replaceAll(WWConstants.replaceSpaces, " ").trim());
        }

        return result;
    }

    /**
     * Filters the html-document for the name of the meal.
     *
     * @param htmlDoc
     * @param searchString
     * @return
     */
    private String searchName(Elements htmlDoc, String searchString){
        String result = "";
        Elements elements = htmlDoc.select(searchString);

        for (Element element : elements) {
            if (!result.isEmpty()){
                result = result + WWConstants.separator;
            }
            result = result + element.text().replaceAll(WWConstants.replaceSpaces, " ").trim();
        }

        return result;
    }
}
