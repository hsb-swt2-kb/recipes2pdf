package sample.parser;

/**
 * Created by fpfennig on 29.05.2016.
 */
public interface CKConstants {

    String SEARCH_API = "http://api.chefkoch.de/api/1.0/api-recipe-search.php";
    String RECIPE_DETAIL_API = "http://api.chefkoch.de/api/1.0/api-recipe.php";


    String numberWithCharacters    = "^(\\d)+([.,]\\d)*([äüöÄÖÜ\\w])*";

    // Keywords
    String cookTime = "cookTime";
    String image = "image";
    String recipeIngredient = "recipeIngredient";
    String name = "name";
    String prepTime = "prepTime";
    String recipeInstructions = "recipeInstructions";
    String recipeYield = "recipeYield";
    String recipeCategory = "recipeCategory";
    String squareBracket = "]";
}
