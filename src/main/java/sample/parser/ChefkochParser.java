package sample.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import sample.external.ChefkochAPI;
import sample.model.IRecipe;

import java.util.Optional;

/**
 * Created by czoeller on 16.04.16.
 * Chefkoch parser to parse recipes from chefkoch.de
 */
public class ChefkochParser extends AParser {

    private ChefkochAPI chefkochAPI;

    public ChefkochParser() {
        this.chefkochAPI = new ChefkochAPI();
    }

    // Used for mock injection in unit tests
    void setChefkochAPI(final ChefkochAPI chefkochAPI) {
        this.chefkochAPI = chefkochAPI;
    }

    /**
     * The parse method uses an Web-API.
     * It might need some time to fetch the recipe details and generate the recipe.
     *
     * @param text The recipe as text
     * @return IRecipe The populated IRecipe
     * @throws Exception
     */
    @Override
    public IRecipe parse(final String text) throws Exception {
        final String showID = extractShowID(text).orElseThrow( () -> new IllegalStateException("Could not extract id from the recipe.") );
        this.recipe = this.chefkochAPI.findById(showID).orElseThrow( () -> new IllegalStateException("Could not parse recipe.") );
        return recipe;
    }

    /**
     * Extracts the <code>showID</code> of a chefkoch recipe.
     *
     * @param text The recipe as text
     * @return The <code>showID</code>
     */
    private Optional<String> extractShowID(final String text) {
        String showID;
        String needle = "rezept_show_id = ";
        Document doc = Jsoup.parse(text);
        Elements scriptTags = doc.getElementsByTag("script");
        for (Element tag : scriptTags) {
            for (DataNode node : tag.dataNodes()) {
                if (node.getWholeData().contains(needle)) {
                    String line = node.getWholeData();
                    final int from = line.indexOf(needle) + needle.length();
                    final int to = line.indexOf(";", from);
                    showID = line.substring(from, to).replace("'", "").trim();
                    return Optional.of(showID);
                }
            }
        }
        return Optional.empty();
    }

    /**
     * The parser accepts recipes with <code>chefkoch</code>(case insensitive) in the recipe text.
     * Improvement note: It might be specified by scanning the html title attribute to avoid false positives.
     *
     * @param text The recipe as text
     * @return true if accepts
     */
    @Override
    public boolean accepts(final String text) {
        return text.contains("chefkoch");
    }
}
