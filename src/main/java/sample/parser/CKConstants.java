package sample.parser;

/**
 * Created by fpfennig on 29.05.2016.
 */
public interface CKConstants {
    public static final String SEARCH_API = "http://api.chefkoch.de/api/1.0/api-recipe-search.php";
    public static final String RECIPE_DETAIL_API = "http://api.chefkoch.de/api/1.0/api-recipe.php";


    public static final String numberWithCharacters    = "^(\\d)+([.,]\\d)*([äüöÄÖÜ\\w])*";

    // Keywords
    public static final String cookTime = "cookTime";
    public static final String image = "image";
    public static final String recipeIngredient = "recipeIngredient";
    public static final String name = "name";
    public static final String prepTime = "prepTime";
    public static final String recipeInstructions = "recipeInstructions";
    public static final String recipeYield = "recipeYield";
    public static final String recipeCategory = "recipeCategory";
    public static final String squareBracket = "]";
}
