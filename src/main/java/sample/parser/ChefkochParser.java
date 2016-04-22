package sample.parser;

import one.util.streamex.StreamEx;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import sample.external.ChefkochAPI;
import sample.model.IRecipe;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Predicate;

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

    /**
     * Extracts the <code>showID</code> of a chefkoch recipe in html format.
     *
     * @param text The recipe as text
     * @return The <code>showID</code>
     */
    private Optional<String> extractShowID(final String text) {
        final Document doc = Jsoup.parse(text);
        final Element head = doc.getElementsByTag("head").first();

        final Optional<Element> metaElementOpt = extractMetaElement(head);
        final Element metaElement = metaElementOpt.orElseThrow(() -> new IllegalStateException("Could not find meta tag to retrieve id from."));
        final String contentURL = metaElement.attr("content");

        final Optional<String> showID = extractIDFromURL(contentURL);
        showID.orElseThrow(() -> new IllegalStateException("Could not retrieve id. Maybe html structure changed."));
        return showID;
    }

    /**
     * Extract meta element with url from head element.
     *
     * E.g. <meta property="og:url" content="http://www.chefkoch.de/rezepte/1616691268862802/Zucchini-Lasagne.html" />
     * @param head The html head.
     * @return Optional<Element> meta tag.
     */
    private Optional<Element> extractMetaElement(Element head) {
        final Optional<Element> metaElementOpt = head.children().stream()
            .filter(headTag -> headTag.tagName().contains("meta"))
            .filter(headTag -> headTag.attributes().get("property").equals("og:url"))
            .findFirst();
        return metaElementOpt;
    }

    /**
     * Extract the showID from url.
     *
     * Since the prefix of the <code>showID</code> is <code>rezepteurl</code> we can parse the url segments.
     * http://www.chefkoch.de/rezepte/1616691268862802/Zucchini-Lasagne.html
     * @param contentURL The url with id.
     * @return Optional<Element> id.
     */
    private Optional<String> extractIDFromURL(String contentURL) {
        // http://www.chefkoch.de/rezepte/1616691268862802/Zucchini-Lasagne.html
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
    }

}
