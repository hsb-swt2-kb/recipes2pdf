package sample.ui;

/**
 * @author Tobias Stelter
 * The Class ''ControllerChangeRecipe'' manages the ChangeRecipe-FXML.
 * It displays the recipe-data and provides methods for changing a recipe.
 * The recipe contains at least a name, ingredients and the preparationtext.
 */


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.exceptions.RecipeNotFoundException;
import sample.model.Recipe;

import java.io.File;

public class ControllerChangeRecipe {


    //Recipe selection from the ControllerManageCookBook
    String name = ControllerManageCookBook.getInstance().getSelectedItem();
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
    String ingredients;
    Recipe recipe;
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
    private String path;
    @FXML
    private Button closeButton;
    @FXML
    private Button changeButton;

    @FXML
    public void initialize() {
        refreshData();


    }

    protected void refreshData() {
        loadInformation();
        fillTextFields();
    }

    /**
     * load recipe information
     */

    private void loadInformation() {
        try{
            recipe = UI.searchRecipe(name);
            ernaehrungsart = recipe.getNurture().getName();
            gerichtart = recipe.getCourse().getName();
            portion = Double.toString(recipe.getPortions());
            region = recipe.getRegion().getName();
            category = recipe.getCategory().getName();
            source = recipe.getSource().getName();
            path = "";
            ingredients = "";
            saison = recipe.getSeason().getName();
            daytime = recipe.getDaytime().getName();
            zubereitungszeit = Integer.toString(recipe.getDuration());
            zubereitungstext = recipe.getText();
        }
        catch (RecipeNotFoundException e){
            System.out.println("Couldn't load recipe");
        }
    }

    /**
     * set loaded text to textfields
     */

    private void fillTextFields() {

        textFieldName.setText(name);
        textFieldErnaehrungsart.setText(ernaehrungsart);
        textFieldGerichtart.setText(gerichtart);
        textFieldPortion.setText(portion);
        textFieldRegion.setText(region);
        textFieldCategory.setText(category);
        textFieldSource.setText(source);
        textFieldSaison.setText(saison);
        textFieldDaytime.setText(daytime);
        textFieldPicture.setText(path);
        textFieldZubereitungszeit.setText(zubereitungszeit);
        textAreaZubereitungstext.setText(zubereitungstext);
    }



    /**
     * The method ''closeChangeRecipe()'' closes the ChangeRecipe-Window after a interaction with the close-button.
     *
     * @param event
     */

        @FXML
        void closeChangeRecipe(ActionEvent event) {
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();

        }

    @FXML
    void changeRecipe(ActionEvent event) {
              if((this.textFieldName.getText().trim().isEmpty() == false)&&(this.textAreaZubereitungstext.getText().trim().isEmpty() == false)){
            recipe.setTitle(getName());
            recipe.setDuration(getZubereitungszeit());
               //   getErnaehrungsart();
               //   getGerichtart();
               //   getRegion();
               //   getCategory();
            recipe.setPortions(Integer.parseInt(getPortion()));
               //   getSource();
               //   getSaison();
               //   getDaytime();
            recipe.setText(getZubereitungstext());
            UI.changeRecipe(recipe);
            ControllerManageCookBooks.getInstance().refreshListViews();
            Stage stage = (Stage) changeButton.getScene().getWindow();
            stage.close();
        }


    }

    /**
     * The method ''openFileChooser()'' opens the filechooser after a interaction with the browse-Button.
     * If the user imports a picture, the path will display in the textField-picture.
     * @param event
     */

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

    private int getZubereitungszeit(){
       zubereitungszeit = textFieldZubereitungszeit.getText();
        int duration = 0;
        try {
            duration = Integer.parseInt(zubereitungszeit);
        }catch (NumberFormatException e){}
        return duration;
    }
    private String getZubereitungstext(){
        zubereitungstext = textAreaZubereitungstext.getText();
        return  zubereitungstext;
    }


}


