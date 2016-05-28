/*package sample.parser;

import sample.model.IRecipe;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Created by czoeller on 31.03.16.
 */
/*public class ParserController {

    private ArrayList<AParser> parsers;

    public ParserController(DemoParser demoParser, ChefkochParser chefkochParser) {
        parsers = new ArrayList<>(5);
        parsers.add( demoParser );
        parsers.add( chefkochParser );
    }

    public Optional<IRecipe> parse(String text) throws Throwable {
        for (AParser parser : parsers) {
            if ( parser.accepts(text) ) {
                return Optional.of( parser.parse(text) );
            }
        }
        return Optional.empty();
    }

}
*/
