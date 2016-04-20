package sample.external;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import sample.model.IRecipe;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Optional;

/**
 * Created by czoeller on 16.04.16.
 */
public class ChefkochAPI {

    private static final String SEARCH_API = "http://api.chefkoch.de/api/1.0/api-recipe-search.php";
    private static final String RECIPE_DETAIL_API = "http://api.chefkoch.de/api/1.0/api-recipe.php";
    private IRecipe recipe;

    public ChefkochAPI() {
        this.recipe = IRecipe.getInstance();
    }

    // Used for mock injection in unit tests
    void setRecipe(IRecipe recipe) {
        this.recipe = recipe;
    }

    public Optional<String> search(String title) {
        String rezeptShowID = null;
        try {
            final HttpResponse<JsonNode> json = Unirest.get(SEARCH_API).queryString("Suchbegriff", URLEncoder.encode(title, "UTF-8") ).asJson();
            final JSONObject object = json.getBody().getObject();
            final JSONArray result = object.getJSONArray("result");
            if( 0 < result.length() ) {
                final JSONObject jsonObject = result.getJSONObject(0);
                rezeptShowID = jsonObject.getString("RezeptShowID");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(rezeptShowID);
    }

    public Optional<IRecipe> findById(String showID) {
        try {
            final HttpResponse<JsonNode> json = Unirest.get(RECIPE_DETAIL_API).queryString("ID", showID).asJson();
            final JsonNode body = json.getBody();
            final JSONObject object = body.getObject();
            final JSONArray result = object.getJSONArray("result");
            final JSONObject list = result.getJSONObject(0);
            recipe.setTitle( list.getString("rezept_name") );
            final JSONArray zutaten = list.getJSONArray("rezept_zutaten");
            for (int i = 0; i < zutaten.length(); i++) {
                final JSONObject jsonObject = zutaten.getJSONObject(i);
                recipe.add( jsonObject.getString("name"), jsonObject.getInt("menge"), jsonObject.optString("einheit") );
            }
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return Optional.of(recipe);
    }

}
