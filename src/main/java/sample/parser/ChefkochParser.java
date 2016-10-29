package sample.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import sample.exceptions.CouldNotParseException;
import sample.model.Recipe;
import sample.model.Source;
import sample.model.util.RecipeUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by czoeller on 16.04.16.
 * Changed by fpfennig on 29.05.16
 * Chefkoch parser to parse recipes from chefkoch.de
 */
public class ChefkochParser extends AConcreteParser {

    /**
     * The parse method uses Jsoup instead of an Web-API.
     * It might need some time to fetch the recipe details and generate the recipe.
     *
     * @param text The recipe as text
     * @return Recipe The populated Recipe
     * @throws Exception
     */
    @Override
    public Recipe parse(final List<String> text) throws CouldNotParseException {
        recipe = new Recipe();

        String format = "";

        for (String entry: text) {
            format = format + " " + entry;
        }

        Document htmlDoc = Jsoup.parse(format.toString());
        Element script = searchForDataScript(htmlDoc);
        getDataFromScript(script);

        if(RecipeUtil.isRecipeIncomplete(recipe)){
            throw new CouldNotParseException("Invalid recipe, mandatory fields are empty!");
        }

        return recipe;

        /*final String showID = extractShowID(text.toString()).orElseThrow( () -> new IllegalStateException("Could not extract id from the recipe.") );
        this.recipe = this.chefkochAPI.findById(showID).orElseThrow( () -> new IllegalStateException("Could not parse recipe.") );
        return recipe;*/
    }

    /**
     * The parser accepts recipes with <code>chefkoch</code>(case insensitive) in the recipe text.
     * Improvement note: It might be specified by scanning the html title attribute to avoid false positives.
     *
     * @param text The recipe as text
     * @return true if accepts
     */
    @Override
    public boolean accepts(final List<String> text) {
        return text.toString().contains("chefkoch");
    }

    /**
     * Method to search for the <script> which contains all the necessary data.
     *
     * @param htmlDoc
     * @return
     */
    private Element searchForDataScript(Document htmlDoc){
        Element result = null;
        Elements elements = htmlDoc.select("script");

        for (Element element : elements) {
            if(element.attr("type").equals("application/ld+json") && element.toString().contains("recipeIngredient")) {
                result = element;
                break;
            }
        }

        return result;
    }

    /**
     * Method to pull all necessary data from the <script>.
     *
     * @param script contains the data <script> from the searchForDataScript-Method
     */
    private void getDataFromScript(Element script){
        /*
          "cookTime": "PT25M",
          "image": "...",
          "recipeIngredient": [
                          "9  Lasagneplatte(n) ",                        "300 g Champignons ",                        "100g Champignons ",                        "2 Champignons ",                        "3.1 g Champignons ",                        "3.2g Champignons ",                        "3,3 g Champignons ",                        "3,4g Champignons ",                        "4.123412313 g Champignons ",                        "4.123412313g Champignons ",                        "4.123512313 g Champignons ",                        "4.223512313g Champignons ",                        "300g1asfdsagfdsgagfgfdagasgsgadsgsggsgfagdäkg0123456789 Champignons1adsfasfaregfadgragafadgagadgfs0123456789 ",                        "300 g1asfdsagfdsgagfgfdagasgsgadsgsggsgfagdäkg0123456789 Champignons1adsfasfaregfadgragafadgagadgfs0123456789 ",                        "3  Zucchini ",                        "1 Dose Tomate(n) , geschält und gestückelt",                        " etwas Olivenöl ",                        "2  Zwiebel(n) ",                        " n. B. Gewürz(e) , z.B. Tessiner Gewürz",                        "1 Becher Mandelsahne , oder Sojasahne",                        " etwas Gemüsebrühepulver ",                        " etwas Salz und Pfeffer ",                        "100 g Feta-Käse "            ],
          "name": "...",
          "prepTime": "PT20M",
          "recipeInstructions": "...",
          "recipeYield": "4",
          "recipeCategory": [
                      "...",              "...",              "..."          ]
        */
        ArrayList<String> workingList = new ArrayList<>(Arrays.asList(script.toString().split("\"")));

        // Remove script header
        for(int counter = 0; counter < 3; counter++) {
            workingList.remove(0);
        }

        // Remove empty entries
        for (int counter = workingList.size() - 1; counter >= 0; counter--){
            if(workingList.get(counter).trim().isEmpty()){
                workingList.remove(counter);
            }
        }

        ArrayList<String> attributeList = getAttributeList(workingList);
        fillAttributes(attributeList);
    }

    /**
     * Method to get a list of the data arrays.
     *
     * Example: text = recipeCategory: [, "Spezielles", "Ernährungskonzepte", "Vegetarisch" ]
     * -> returns a list containing: Spezielles, Ernährungskonzepte and Vegetarisch.
     *
     * @param text
     * @return
     */
    private ArrayList<String> getAttributeArrayValue(String text){
        ArrayList<String> result = new ArrayList<>();

        // Remove unnecessary parts
        String[] working = text.split("\\[");
        String work = working[1].trim();
        working = work.split("\\]");
        work = working[0].trim();

        // Splitting the "array"
        working = work.split("\",");

        for(int counter = 0; counter < working.length; counter++){
            result.add(working[counter].replaceAll("\"","").trim());
        }

        return result;
    }

    /**
     * Method to get the value of one attribute.
     *
     * Example: text = name: Zucchini-Pilz-Lasagne mit Feta-Topping
     * -> return string: Zucchini-Pilz-Lasagne mit Feta-Topping
     *
     * @param text
     * @return
     */
    private String getAttributeValue(String text){
        String result = "";
        String[] working = text.split(": ");

        result = working[1].trim();

        // Removing some parsing errors
        result = result.replaceAll(", ,", "");

        return result;
    }

    /**
     * Method to get the time to cook/prepare.
     *
     * @param text
     * @return
     */
    private String getTime(String text){
        String result = "";
        String[] working = text.split(": ");

        // Removing non numeral characters
        Pattern regexPattern = Pattern.compile(WWConstants.notANumber);
        Matcher regexMatcher = regexPattern.matcher(working[1]);

        if(regexMatcher.find()){
            result = working[1].replaceAll(WWConstants.notANumber, "").trim();
        }

        return result;
    }

    /**
     * Method to add all the data to the recipe.
     *
     * @param attributeList
     */
    private void fillAttributes(ArrayList<String> attributeList){
        ArrayList<String> time = new ArrayList<>();

        for (int counter = 0; counter < attributeList.size(); counter++){
            if(attributeList.get(counter).contains(CKConstants.cookTime)){
                time.add(getTime(attributeList.get(counter)));
            }

            if(attributeList.get(counter).contains(CKConstants.image)){
                String image = getAttributeValue(attributeList.get(counter));
                byte[] img = lib.downloadImage(image);
                if(img != null) {
                    recipe.setImage(img);
                }
            }

            if(attributeList.get(counter).contains(CKConstants.recipeIngredient)){
                ArrayList<String> arrayValues = getAttributeArrayValue(attributeList.get(counter));
                ArrayList<String[]> ingredientsList = lib.convertIngredientList(arrayValues);
                setRecipeIngredientsList(ingredientsList);
            }

            if(attributeList.get(counter).contains(CKConstants.name)){
                String title = getAttributeValue(attributeList.get(counter));
                if(title != null) {
                    recipe.setTitle(title);
                }
            }

            if(attributeList.get(counter).contains(CKConstants.prepTime)){
                time.add(getTime(attributeList.get(counter)));
            }

            if(attributeList.get(counter).contains(CKConstants.recipeInstructions)){
                String text = getAttributeValue(attributeList.get(counter));
                if(text != null) {
                    recipe.setText(text);
                }
            }

            if(attributeList.get(counter).contains(CKConstants.recipeYield)){
                int servings = 0;
                try {
                    servings = Integer.parseInt(getAttributeValue(attributeList.get(counter)));
                }
                catch (Exception e){
                    // TODO: Exception handling
                }
                recipe.setPortions(servings);
            }

            if(attributeList.get(counter).contains(CKConstants.recipeCategory)){
                ArrayList<String> arrayValues = getAttributeArrayValue(attributeList.get(counter));
                // TODO: Category
            }
        }

        int preparingTime = 0;

        for(int counter = 0; counter < time.size(); counter++){
            try {
                preparingTime = preparingTime + Integer.parseInt(time.get(counter));
            }
            catch (Exception e){
                // TODO: Exception handling
            }
        }

        Source source = new Source();
        source.setName("Chefkoch");
        recipe.setSource(source);

        recipe.setDuration(preparingTime);
    }

    /**
     * Method to put together the necessary fields for further refining.
     *
     * @param completeList
     * @return
     */
    private ArrayList<String> getAttributeList(ArrayList<String> completeList){
        ArrayList<String> result = new ArrayList<>();
        boolean checkIngredients = false;
        boolean checkCategory = false;
        String attributeIngredient = "\"";
        String attributeCategory = "\"";

        for (int counter = 0; counter < completeList.size(); counter++){
            if(completeList.get(counter).contains(CKConstants.cookTime)){
                result.add(completeList.get(counter) + completeList.get(counter + 1) + completeList.get(counter + 2));
            }

            if(completeList.get(counter).contains(CKConstants.image)){
                result.add(completeList.get(counter) + completeList.get(counter + 1) + completeList.get(counter + 2));
            }

            if(completeList.get(counter).contains(CKConstants.recipeIngredient) || checkIngredients){
                attributeIngredient = attributeIngredient + completeList.get(counter) + "\"";

                if(completeList.get(counter).contains(CKConstants.recipeIngredient)){
                    checkIngredients = true;
                }
                if(completeList.get(counter).contains(CKConstants.squareBracket)){
                    checkIngredients = false;
                    result.add(attributeIngredient);
                }
            }

            if(completeList.get(counter).contains(CKConstants.name)){
                result.add(completeList.get(counter) + completeList.get(counter + 1) + completeList.get(counter + 2));
            }

            if(completeList.get(counter).contains(CKConstants.prepTime)){
                result.add(completeList.get(counter) + completeList.get(counter + 1) + completeList.get(counter + 2));
            }

            if(completeList.get(counter).contains(CKConstants.recipeInstructions)){
                result.add(completeList.get(counter) + completeList.get(counter + 1) + completeList.get(counter + 2));
            }

            if(completeList.get(counter).contains(CKConstants.recipeYield)){
                result.add(completeList.get(counter) + completeList.get(counter + 1) + completeList.get(counter + 2));
            }

            if(completeList.get(counter).contains(CKConstants.recipeCategory) || checkCategory){
                attributeCategory = attributeCategory + completeList.get(counter) + "\"";

                if(completeList.get(counter).contains(CKConstants.recipeCategory)){
                    checkCategory = true;
                }
                if(completeList.get(counter).contains(CKConstants.squareBracket)){
                    checkCategory = false;
                    result.add(attributeCategory);
                }
            }
        }

        return result;
    }
}
