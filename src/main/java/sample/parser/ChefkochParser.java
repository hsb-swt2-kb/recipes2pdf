package sample.parser;

import one.util.streamex.StreamEx;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import sample.exceptions.CouldNotParseException;
import sample.model.Recipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by czoeller on 16.04.16.
 * Changed by fpfennig on 29.05.16
 * Chefkoch parser to parse recipes from chefkoch.de
 */
public class ChefkochParser extends AConcreteParser {
    private Recipe recipe = new Recipe();
    private HTMLParserLib lib = new HTMLParserLib();

    //private ChefkochAPI chefkochAPI;

    public ChefkochParser() {
        //this.chefkochAPI = new ChefkochAPI();
    }

    /*
    // Used for mock injection in unit tests
    void setChefkochAPI(final ChefkochAPI chefkochAPI) {
        this.chefkochAPI = chefkochAPI;
    }*/

    /**
     * The parse method uses Jsoup instead of an Web-API.
     * It might need some time to fetch the recipe details and generate the recipe.
     *
     * @param text The recipe as text
     * @return IRecipe The populated IRecipe
     * @throws Exception
     */
    @Override
    public Recipe parse(final ArrayList<String> text) throws Exception {

        Document htmlDoc = Jsoup.parse(text.toString());
        Element script = searchForDataScript(htmlDoc);
        getDataFromScript(script);

        if(recipe.isIncomplete()){
            throw new CouldNotParseException("Invalid recipe, mandatory fields are empty!");
        }

        return recipe;

        /*final String showID = extractShowID(text.toString()).orElseThrow( () -> new IllegalStateException("Could not extract id from the recipe.") );
        this.recipe = this.chefkochAPI.findById(showID).orElseThrow( () -> new IllegalStateException("Could not parse recipe.") );
        return recipe;*/
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
     * Method to put the IngredientsList into the recipe.
     *
     * @param ingredientsList ArrayList<String[]> containing all the ingredients split into amount, unit and ingredient.
     */
    private void setRecipeIngredientsList(ArrayList<String[]> ingredientsList){
        // TODO: Zutaten zu Recipe hinzufügen.
        for (String[] ingredient : ingredientsList) {
            if(ingredient[0].isEmpty()){
                ingredient[0] = "0";
            }

            // TODO: amount = Integer?
            double amount = 0;
            try{
                amount = Double.parseDouble(ingredient[0]);
                amount = lib.round(amount);
                System.out.println( "Zutat: '" + amount + "' '" + ingredient[1] + "' '" + ingredient[2] + "'");
                // amount double, not integer!
                //recipe.add(ingredient[2], amount, ingredient[1]);
            }
            catch (Exception e){
                // TODO: exception handling? maybe ignore?
            }
        }
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
        String[] working = text.split("\\[,");
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
                recipe.setImage(lib.downloadImage(image));
            }

            if(attributeList.get(counter).contains(CKConstants.recipeIngredient)){
                ArrayList<String> arrayValues = getAttributeArrayValue(attributeList.get(counter));
                ArrayList<String[]> ingredientsList = lib.convertIngredientList(arrayValues);
                setRecipeIngredientsList(ingredientsList);
            }

            if(attributeList.get(counter).contains(CKConstants.name)){
                recipe.setTitle(getAttributeValue(attributeList.get(counter)));
            }

            if(attributeList.get(counter).contains(CKConstants.prepTime)){
                time.add(getTime(attributeList.get(counter)));
            }

            if(attributeList.get(counter).contains(CKConstants.recipeInstructions)){
                recipe.setText(getAttributeValue(attributeList.get(counter)));
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

        recipe.setDuration(preparingTime);
    }

    /**
     * Method to put together the necessary fields for further refining.     *
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

    /**
     * The parser accepts recipes with <code>chefkoch</code>(case insensitive) in the recipe text.
     * Improvement note: It might be specified by scanning the html title attribute to avoid false positives.
     *
     * @param text The recipe as text
     * @return true if accepts
     */
    @Override
    public boolean accepts(final ArrayList<String> text) {
        return text.toString().contains("chefkoch");
    }

    /**
     * Extracts the <code>showID</code> of a chefkoch recipe in html format.
     *
     * @param text The recipe as text
     * @return The <code>showID</code>
     */
    /*private Optional<String> extractShowID(final String text) {
        final Document doc = Jsoup.parse(text);
        final Element head = doc.getElementsByTag("head").first();

        final Optional<Element> metaElementOpt = extractMetaElement(head);
        final Element metaElement = metaElementOpt.orElseThrow(() -> new IllegalStateException("Could not find meta tag to retrieve id from."));
        final String contentURL = metaElement.attr("content");

        final Optional<String> showID = extractIDFromURL(contentURL);
        showID.orElseThrow(() -> new IllegalStateException("Could not retrieve id. Maybe html structure changed."));
        return showID;
    }*/

    /**
     * Extract meta element with url from head element.
     *
     * E.g. <meta property="og:url" content="http://www.chefkoch.de/rezepte/1616691268862802/Zucchini-Lasagne.html" />
     * @param head The html head.
     * @return Optional<Element> meta tag.
     */
    /*private Optional<Element> extractMetaElement(final Element head) {
        final Optional<Element> metaElementOpt = head.children().stream()
            .filter(headTag -> headTag.tagName().contains("meta"))
            .filter(headTag -> headTag.attributes().get("property").equals("og:url"))
            .findFirst();
        return metaElementOpt;
    }*/

    /**
     * Extract the showID from url.
     *
     * Since the prefix of the <code>showID</code> is <code>rezepteurl</code> we can parse the url segments.
     * http://www.chefkoch.de/rezepte/1616691268862802/Zucchini-Lasagne.html
     * @param contentURL The url with id.
     * @return Optional<Element> id.
     */
    /*private Optional<String> extractIDFromURL(final String contentURL) {
        final Predicate<String> notEmpty = (String it) -> !it.isEmpty();
        final BiFunction<String, String, String> currentRecipeNextID = (String current, String next) -> {
            if (current.equals("rezepte")) return next;
            return "";
        };
        final String[] segments = contentURL.split("/");

        final Optional<String> showIDOpt = StreamEx.of(segments)
            .pairMap(currentRecipeNextID)
            .filter(notEmpty)
            .findFirst();

        return showIDOpt;
    }*/

}
