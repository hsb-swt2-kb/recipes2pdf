package sample.ui.adapter;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 * Created by noex_ on 31.01.2017.
 */
public class ListViewModelRenderer<T extends GUIRepresentable> implements Callback<ListView<T>, ListCell<T>> {

    @Override
    public ListCell<T> call(ListView<T> param) {
        return new ListCell<T>() {
            @Override
            protected void updateItem(T model, boolean empty) {
                super.updateItem(model, empty);
                if (model != null) {
                    setText(model.getTitle());
                } else {
                    setText(null);
                }
            }
        };
    }
}
