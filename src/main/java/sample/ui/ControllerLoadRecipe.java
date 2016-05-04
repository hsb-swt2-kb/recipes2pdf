package sample.ui;

/**
 * Created by Tobias on 30.04.2016.
 */
import javafx.fxml.FXML;
import javafx.event.*;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class ControllerLoadRecipe {

    @FXML
    private RadioButton radioButtonHyperLink;

    @FXML
    private Text hyperLinkLabel;

    @FXML
    private TextField hyperLinkTextField;

    private boolean editability = false;

    void setHyperLinkEditable() {
        hyperLinkTextField.setEditable(true);

    }
    void setHyperLinkNotEditable() {
        hyperLinkTextField.setEditable(false);
        hyperLinkTextField.clear();
    }

    @FXML
    void changeHyperLinkEditability(ActionEvent event) {
        if (editability == false)
        {
            editability = true;
            setHyperLinkEditable();
        }else{
            editability = false;
            setHyperLinkNotEditable();
        }

    }


}


