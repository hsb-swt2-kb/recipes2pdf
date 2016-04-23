package sample.external;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sample.model.IRecipe;
import sample.util.Image;

import java.io.File;
import java.io.InputStream;
import java.util.Optional;

/**
 * Created by czoeller on 16.04.16.
 */
public class ChefkochAPI {

    public static final String SEARCH_API = "http://api.chefkoch.de/api/1.0/api-recipe-search.php";
    public static final String RECIPE_DETAIL_API = "http://api.chefkoch.de/api/1.0/api-recipe.php";
    final Logger LOG = LoggerFactory.getLogger(this.getClass());
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
            LOG.error("Error while searching API.", e);
        }
        return Optional.ofNullable(rezeptShowID);
    }

    public Optional<IRecipe> findById(String showID) {
        try {
            String url = "";
            final JSONObject body = query(RECIPE_DETAIL_API, "ID", showID );
            final JSONArray result = body.getJSONArray("result");
            final JSONObject jsonRecipe = result.getJSONObject(0);
            recipe.setTitle(jsonRecipe.getString("rezept_name"));

            final JSONArray rezept_bilder = jsonRecipe.getJSONArray("rezept_bilder");
            for (int i = 0; i < rezept_bilder.length(); i++) {
                final JSONObject jsonPictures = rezept_bilder.getJSONObject(i);
                final JSONObject big = jsonPictures.getJSONObject("big");
                url = big.getString("file");
                break;
            }
            downloadAndSetImage(url);

            final JSONArray zutaten = jsonRecipe.getJSONArray("rezept_zutaten");
            for (int i = 0; i < zutaten.length(); i++) {
                final JSONObject jsonObject = zutaten.getJSONObject(i);
                recipe.add( jsonObject.getString("name"), jsonObject.getInt("menge"), jsonObject.optString("einheit") );
            }
        } catch (UnirestException e) {
            LOG.error("Error while retrieving recipe details from API.", e);
        }
        return Optional.of(recipe);
    }

    void downloadAndSetImage(String url) {
        Unirest.get(url).asBinaryAsync(new Callback<InputStream>() {
            @Override
            public void completed(HttpResponse<InputStream> inputStreamHttpResponse) {
                final Image image = new Image(inputStreamHttpResponse.getBody());
                byte[] bytes = image.getBytes();
                recipe.setImage(bytes);
            }

            @Override
            public void failed(UnirestException e) {
                LOG.error("Download of image failed.", e);
            }

            @Override
            public void cancelled() {
                LOG.error("Download of image cancelled.");
            }
        });
    }
}
