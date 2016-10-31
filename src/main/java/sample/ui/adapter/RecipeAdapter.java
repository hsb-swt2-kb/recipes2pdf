package sample.ui.adapter;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import sample.model.Recipe;

/**
 * Created by noex_ on 30.10.2016.
 */
public class RecipeAdapter implements Callback<ListView<Recipe>, ListCell<Recipe>> {
    @Override
    public ListCell<Recipe> call(ListView<Recipe> param) {
        return new ListCell<Recipe>() {
            @Override
            protected void updateItem(Recipe recipe, boolean empty) {
                super.updateItem(recipe, empty);
                if (recipe != null) {
                    setText(recipe.getTitle());
                } else {
                    setText(null);
                }
            }
        };
    }
}
