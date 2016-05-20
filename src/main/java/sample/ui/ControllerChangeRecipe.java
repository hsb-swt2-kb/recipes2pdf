package sample.ui;

/**
 * @author Tobias Stelter
 */


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.File;

public class ControllerChangeRecipe {


    @FXML
    private TextField textFieldName;

    @FXML
    private TextArea textAreaZubereitungstext;

    @FXML
    private TextField textFieldZubereitungszeit;

    @FXML
    private TextField textFieldErnaehrungsart;

    @FXML
    private TextField textFieldGerichtart;

    @FXML
    private TextField textFieldPortion;

    @FXML
    private TextField textFieldRegion;

    @FXML
    private TextField textFieldCategory;

    @FXML
    private TextField textFieldSource;

    @FXML
    private TextField textFieldSaison;

    @FXML
    private TextField textFieldDaytime;

    @FXML
    private Button fileChooserButton;

    @FXML
    private TextField textFieldPicture;

        @FXML
        private Button closeButton;

    @FXML
    private Button changeButton;

    String name;
    String ernaehrungsart;
    String gerichtart;
    String portion;
    String region;
    String category;
    String source;
    String saison;
    String daytime;
    String zubereitungszeit;
    String zubereitungstext;


        @FXML
        void closeChangeRecipe(ActionEvent event) {
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();

        }

    @FXML
    void changeRecipe(ActionEvent event) {
        getName();
        getErnaehrungsart();
        getGerichtart();
        getPortion();
        getRegion();
        getCategory();
        getSource();
        getSaison();
        getDaytime();
        getZubereitungstext();
        getZubereitungszeit();

        if((this.textFieldName.getText().trim().isEmpty() == false)&&(this.textAreaZubereitungstext.getText().trim().isEmpty() == false)){
            System.out.println(name+ernaehrungsart+gerichtart+portion+region+category+source+saison+daytime+zubereitungstext+zubereitungszeit);

            Stage stage = (Stage) changeButton.getScene().getWindow();
            stage.close();
        }



    }

    @FXML
    void openFileChooser(ActionEvent event) {
        FileHandler fileHandler = new FileHandler();
        File file = fileHandler.importPicture();
        if(file != null) {
            textFieldPicture.setText(file.getAbsolutePath());
        }


    }

    private String getErnaehrungsart(){
        ernaehrungsart = textFieldErnaehrungsart.getText();
        return ernaehrungsart;
    }
    private String getName(){
        name = textFieldName.getText();
        return  name;
    }

    private String getGerichtart(){
        gerichtart = textFieldGerichtart.getText();
        return  gerichtart;
    }

    private String getRegion(){
        region = textFieldRegion.getText();
        return  region;
    }

    private String getPortion(){
       portion = textFieldPortion.getText();
        return  portion;
    }

    private String getCategory(){
        category = textFieldCategory.getText();
        return  category;
    }

    private String getSource(){
        source = textFieldSource.getText();
        return  source;
    }

    private String getSaison(){
        saison = textFieldSaison.getText();
        return  saison;
    }

    private String getDaytime(){
       daytime = textFieldDaytime.getText();
        return  daytime;
    }

    private String getZubereitungszeit(){
       zubereitungszeit = textFieldZubereitungszeit.getText();
        return  zubereitungszeit;
    }
    private String getZubereitungstext(){
        zubereitungstext = textAreaZubereitungstext.getText();
        return  zubereitungstext;
    }


}


