package sample.external;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import sample.model.IRecipe;

import java.util.Optional;

/**
 * Created by czoeller on 16.04.16.
 */
public class ChefkochAPI {

    public static final String SEARCH_API = "http://api.chefkoch.de/api/1.0/api-recipe-search.php";
    public static final String RECIPE_DETAIL_API = "http://api.chefkoch.de/api/1.0/api-recipe.php";
    private IRecipe recipe;

    public ChefkochAPI() {
        this.recipe = IRecipe.getInstance();
    }

    // Used for mock injection in unit tests
    void setRecipe(IRecipe recipe) {
        this.recipe = recipe;
    }

    JSONObject query(String url, String key, String value) throws UnirestException {
        HttpResponse<JsonNode> json = Unirest.get(url).queryString(key, value).asJson();
        JSONObject body = json.getBody().getObject();
        return body;
    }

    public Optional<String> search(String title) {
        String rezeptShowID = null;
        try {
            final JSONObject body = query(SEARCH_API, "Suchbegriff", title );
            final JSONArray result = body.getJSONArray("result");
            if( 0 < result.length() ) {
                final JSONObject jsonObject = result.getJSONObject(0);
                rezeptShowID = jsonObject.getString("RezeptShowID");
            }
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(rezeptShowID);
    }

    public Optional<IRecipe> findById(String showID) {
        try {
            final JSONObject body = query(RECIPE_DETAIL_API, "ID", showID );
            final JSONArray result = body.getJSONArray("result");
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
