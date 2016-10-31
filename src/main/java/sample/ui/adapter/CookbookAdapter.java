package sample.ui.adapter;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import sample.model.Cookbook;

/**
 * Created by noex_ on 30.10.2016.
 */
public class CookbookAdapter implements Callback<ListView<Cookbook>, ListCell<Cookbook>> {
    @Override
    public ListCell<Cookbook> call(ListView<Cookbook> param) {
        return new ListCell<Cookbook>() {
            @Override
            protected void updateItem(Cookbook cookbook, boolean empty) {
                super.updateItem(cookbook, empty);
                if (cookbook != null) {
                    setText(cookbook.getTitle());
                } else {
                    setText(null);
                }
            }
        };
    }
}
