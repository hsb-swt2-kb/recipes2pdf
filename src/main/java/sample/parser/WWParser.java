package sample.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import sample.exceptions.CouldNotParseException;
import sample.model.Course;
import sample.model.ISource;
import sample.model.Recipe;
import sample.model.Source;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * implemented by fpfennig on 18.05.2016
 */
class WWParser extends AHTMLParser implements WWConstants{

    /**
     * The parse method is used to parse the weight watchers HTML recipe.
     * For that it first decides the version of the document, and then chooses the right methods.
     *
     * @param text The recipe content as text
     * @return Recipe The populated Recipe
     * @throws CouldNotParseException
     */
    @Override
    public Recipe parse(final List<String> text) throws CouldNotParseException {
        recipe = new Recipe();

        String format = "";

        for (String entry: text) {
            format = format + " " + entry;
        }

        Document htmlDoc = Jsoup.parse(format);

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

        if(recipe.isIncomplete()){
            throw new CouldNotParseException("Invalid recipe, mandatory fields are empty!");
        }

        return recipe;
    }

    /**
     * The parser accepts recipes with <code>WeightWatchers</code>(case insensitive) in the recipe text.
     * Improvement note: It might be specified by scanning the html title attribute to avoid false positives.
     *
     * @param text The recipe as text
     * @return true if accepts
     */
    @Override
    public boolean accepts(final List<String> text) {
        return text.toString().contains("weightwatchers");
    }

    /**
     * The parse method for the 2015 version of the recipes.
     *
     * @param htmlDoc The parsed recipe as a JSoup Document
     * @return void
     */
    private void parseVersion2015(Document htmlDoc){
        Elements elements = htmlDoc.getAllElements();
        String name = this.searchName(elements, WWConstants.name2015);

        List<String> images = this.searchImage(elements, WWConstants.image2015);
        byte[] image = null;
        if(images.size() == 1) {
            image = lib.downloadImage(images.get(0));
        }

        int preparingTime = this.searchPreparingTime2015(elements);

        int servings = this.searchServings(elements);

        String type = this.searchType2015(elements);
        Course course = new Course();
        course.setName(type);

        List<String> ingredientsListToConvert = this.searchIngredients2015(elements);
        List<String[]> ingredientsList = lib.convertIngredientList(ingredientsListToConvert);

        String description = this.searchDescription2015(elements);

        if(name != null) {
            recipe.setTitle(name);
        }
        if(image != null) {
            recipe.setImage(image);
        }
        recipe.setDuration(preparingTime);
        if(servings > 0){
            recipe.setPortions(servings);
        }
        if(type != null) {
            recipe.setCourse(course);
        }
        setRecipeIngredientsList(ingredientsList);
        if(description != null) {
            recipe.setText(description);
        }
        ISource source = new Source();
        source.setName("Weightwatchers");
        recipe.setSource(source);
    }

    /**
     * The parse method for the 2016 version of the recipes.
     *
     * @param htmlDoc The parsed recipe as a JSoup Document
     * @return void
     */
    private void parseVersion2016(Document htmlDoc){
        Elements elements = htmlDoc.select(WWConstants.htmlDoc2016);

        String name = this.searchName(elements, WWConstants.name2016);

        List<String> images = this.searchImage(elements, WWConstants.image2016);
        byte[] image = null;
        if(images.size() == 1) {
            image = lib.downloadImage(images.get(0));
        }

        int servings = this.searchServings(elements);

        String type = this.searchType(elements);
        Course course = new Course();
        course.setName(type);

        List<String> ingredientsListToConvert = this.searchIngredients2016(elements);
        List<String[]> ingredientsList = lib.convertIngredientList(ingredientsListToConvert);

        String description = this.searchDescription2016(elements);

        if(name != null) {
            recipe.setTitle(name);
        }
        if(image != null) {
            recipe.setImage(image);
        }
        if(servings > 0){
            recipe.setPortions(servings);
        }
        if(type != null) {
            recipe.setCourse(course);
        }
        setRecipeIngredientsList(ingredientsList);
        if(description != null) {
            recipe.setText(description);
        }
        ISource source = new Source();
        source.setName("Weightwatchers");
        recipe.setSource(source);
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
     * @return The type of meal
     */
    private String searchType2015(Elements htmlDoc){
        Elements elements = htmlDoc.select(WWConstants.type2015);

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

        if(result.length() > WWConstants.maxFieldSize){
            result = result.substring(0, WWConstants.maxFieldSize);
        }

        return result;
    }

    /**
     * Search for the description of the recipe. The description is split in multiple parts, which are scattered over
     * multiple paragraphs. So for every part needs to be checked if one or more paragraphs are used.
     *
     * @param htmlDoc The parsed recipe as a JSoup Document
     * @return The description of the recipe
     */
    private String searchDescription2015(Elements htmlDoc){
        String result = "";
        Elements htmlElements = htmlDoc.select(WWConstants.ingredientsAndDescr2015);

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
     * @return The description of the recipe
     */
    private String searchDescription2016(Elements htmlDoc){
        String result = "";
        Elements elements = htmlDoc.select(WWConstants.tableTd);

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
     * @param htmlDoc html document to parse
     * @return List<String> with ????
     */
    private List<String> searchIngredients2015(Elements htmlDoc){
        Elements elements = htmlDoc.select(WWConstants.ingredientsAndDescr2015);
        elements = elements.select(WWConstants.tableTd);
        List<String> result = new ArrayList<>();

        for (Element element: elements){
            if (element.hasText() && !element.text().replaceAll(WWConstants.replaceSpaces," ").trim().isEmpty()) {
                String working = element.text().replaceAll(WWConstants.replaceSpaces, " ").trim();

                // Remove some double spaces
                while(working.contains("  ")){
                    working = working.replaceAll("( ){2}", " ");
                }

                result.add(working);
            }
        }

        return result;
    }

    /**
     * Searching for ingredients entries in the 2016 version.
     *
     * @param htmlDoc html document to parse
     * @return List<String> with ????
     */
    private List<String> searchIngredients2016(Elements htmlDoc){
        List<String> result = new ArrayList<>();
        Elements elements = htmlDoc.select(WWConstants.ingredients2016);

        for (Element element: elements){
            if (element.text().contains(WWConstants.ingredientsTag)) {

                // Old version, problem with finding new lines
                //String workingString = element.text().replaceAll(WWConstants.replaceSpaces," ").trim();

                String workingString = element.getAllElements().first().html().replaceAll(WWConstants.replaceSpaces," ");
                workingString = workingString.replaceAll(WWConstants.version2016LineBreaks, "\n");

                // Remove some double spaces
                while(workingString.contains("  ")){
                    workingString = workingString.replaceAll("( ){2}", " ");
                }

                String[] working = workingString.split("\n");

                // Putting the splitted results into the list, with an empty entry filter
                boolean firstTime = true; // Ignoring "Zutaten" entry

                for (String splittedString : working) {
                    if (!splittedString.isEmpty()) {
                        if(!firstTime) {
                            result.add(splittedString.trim());
                        }
                        else{
                            firstTime = false;
                        }
                    }
                }
            }
        }

        return result;
    }

    /**
     * Filtering the html-document for the preparing time of the meal.
     *
     * @param htmlDoc html document to parse
     * @return int preparation time
     */
    private int searchPreparingTime2015(Elements htmlDoc){
        int result = 0;
        String working = "";
        Elements elements = htmlDoc.select(WWConstants.preparingTime2015);
        // Description needs to be filtered
        boolean firstTime = true;

        for (Element element : elements) {
            // Protected spaces needs to be filtered
            String[] splits = element.text().replaceAll(WWConstants.replaceSpaces, " ").trim().split(" ");

            // Remove some double spaces
            while(working.contains("  ")){
                working = working.replaceAll("( ){2}", " ");
            }

            for (String split : splits) {
                if(!firstTime) {
                    if (!split.trim().isEmpty()) {
                        if (!working.isEmpty()) {
                            working = working + " ";
                        }
                        working = working + split.trim();
                    }
                }
                else {
                    firstTime = false;
                }
            }
        }

        // Removing non numeral characters
        Pattern regexPattern = Pattern.compile(WWConstants.notANumber);
        Matcher regexMatcher = regexPattern.matcher(working);

        if(regexMatcher.find()){
            working = working.replaceAll(WWConstants.notANumber, "").trim();
        }
        if(!working.isEmpty() && working.matches(WWConstants.onlyNumber)){

            if(working.length() > WWConstants.maxFieldSize){
                working = working.substring(0, WWConstants.maxFieldSize);
            }
            try {
                result = Integer.parseInt(working);
            }
            catch(NumberFormatException nfe){
                result = 0;
            }
        }
        return result;
    }

    /**
     * Filtering the html-document for the number of servings, returns 0 if no valid entry was found.
     *
     * @param htmlDoc html document to parse
     * @return int servings
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
        Pattern regexPattern = Pattern.compile(WWConstants.notANumber);
        Matcher regexMatcher = regexPattern.matcher(working);

        if(regexMatcher.find()){
            //noinspection ResultOfMethodCallIgnored
            working.replaceAll(WWConstants.notANumber, "");
        }

        if(!working.isEmpty() && working.matches(WWConstants.onlyNumber)){
            if(working.length() > WWConstants.maxFieldSize){
                working = working.substring(0, WWConstants.maxFieldSize);
            }
            try {
                result = Integer.parseInt(working);
            }
            catch(Exception e){
                result = 0;
            }
        }

        return result;
    }

    /**
     * Filtering the html-document for images, returns a list of ALL images found.
     *
     * @param htmlDoc  html document to parse
     * @param searchString string to search for
     * @return List<String> with ???
     */
    private List<String> searchImage(Elements htmlDoc, String searchString){
        Elements elements = htmlDoc.select(searchString).select(WWConstants.imgTag);
        return elements.stream().map(element -> element.attr(WWConstants.srcAttr).replaceAll(WWConstants.replaceSpaces, " ").trim()).collect(Collectors.toList());
    }

    /**
     * Filters the html-document for the name of the meal.
     *
     * @param htmlDoc html document to parse
     * @param searchString search for this name
     * @return String with ????
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

        if(result.length() > WWConstants.maxFieldSize){
            result = result.substring(0, WWConstants.maxFieldSize);
        }
        return result;
    }
}
