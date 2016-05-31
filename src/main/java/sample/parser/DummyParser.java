package sample.parser;

import sample.model.Recipe;

import java.util.ArrayList;

/**
 * implemented by on 31.05.16 by markus
 */
public class DummyParser extends AConcreteParser {
    @Override
    public Recipe parse(ArrayList<String> text) throws Exception {
        Recipe recipe = new Recipe();
        recipe.setTitle("Testrezept");
        recipe.setText("Testzubereitungstext");
        recipe.add("Milch",2,"Liter");
        return recipe;
    }

    @Override
    public boolean accepts(ArrayList<String> text) {
        return true;
    }
}
