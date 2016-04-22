package sample.external;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import sample.model.IRecipe;

import java.io.*;
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
            recipe.setImage(downloadImage(url));

            final JSONArray zutaten = jsonRecipe.getJSONArray("rezept_zutaten");
            for (int i = 0; i < zutaten.length(); i++) {
                final JSONObject jsonObject = zutaten.getJSONObject(i);
                recipe.add( jsonObject.getString("name"), jsonObject.getInt("menge"), jsonObject.optString("einheit") );
            }
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return Optional.of(recipe);
    }

    public byte[] downloadImage(String url) {
        final HttpResponse<InputStream> inputStreamHttpResponse;
        try {
            inputStreamHttpResponse = Unirest.get(url).asBinary();
            byte[] buffer = new byte[1024];
            int bytesRead;
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            while ((bytesRead = inputStreamHttpResponse.getBody().read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
            createImage(output.toByteArray()); // TODO REMOVE
            return output.toByteArray();

        } catch (UnirestException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0]; // TODO CHECK
    }

    private void createImage(byte[] bytes) {
        File f = new File("out.jpeg");
        System.out.println(f.getAbsolutePath());
        try {
            FileOutputStream outputStream = new FileOutputStream(f);
            outputStream.write(bytes);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
