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
import sample.model.*;

import java.io.File;

public class ControllerChangeRecipe {

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
        Recipe recipe;
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
            //Recipe selection from the ControllerManageCookBook
            recipe = UI.searchRecipe(ControllerManageCookBook.getInstance().getSelectedRecipes().get(0));
        }
        catch (RecipeNotFoundException e){
            System.out.println("Couldn't load recipe.");
        }
    }

    /**
     * set loaded text to textfields
     */
    private void fillTextFields() {

        textFieldName.setText(recipe.getTitle());
        textFieldErnaehrungsart.setText(recipe.getNurture().getName());
        textFieldGerichtart.setText(recipe.getCourse().getName());
        textFieldPortion.setText(Integer.toString(recipe.getPortions()));
        textFieldRegion.setText(recipe.getRegion().getName());
        textFieldCategory.setText(recipe.getCategory().getName());
        textFieldSource.setText(recipe.getSource().getName());
        textFieldSaison.setText(recipe.getSeason().getName());
        textFieldDaytime.setText(recipe.getDaytime().getName());
        textFieldPicture.setText(""); // TODO: handle picturepath
        textFieldZubereitungszeit.setText(""); // TODO: Zubereitungszeit behandeln
        textAreaZubereitungstext.setText(recipe.getText());
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

    private void manageSaveError(String boldPrint, String littlePrint) {
        ControllerDefault controllerDefault = new ControllerDefault();
        controllerDefault.newWindowNotResizable(Resources.getErrorFXML(), Resources.getErrorWindowText());
        ControllerError.getInstance().setLabels(boldPrint, littlePrint);
    }

    @FXML
    void changeRecipe(ActionEvent event) {
        if((this.textFieldName.getText().trim().isEmpty() == false)&&(this.textAreaZubereitungstext.getText().trim().isEmpty() == false)){
            recipe.setTitle(textFieldName.getText());
            recipe.setDuration(getZubereitungszeit());

            INurture nurture = new Nurture();
            nurture.setName(textFieldErnaehrungsart.getText());
            recipe.setNurture(nurture);

            ICourse course = new Course();
            course.setName(textFieldGerichtart.getText());
            recipe.setCourse(course);

            IRegion region = new Region();
            region.setName(textFieldRegion.getText());
            recipe.setRegion(region);

            ICategory category=new Category();
            category.setName(textFieldCategory.getText());
            recipe.setCategory(category);

            recipe.setPortions(Integer.parseInt(textFieldPortion.getText()));

            ISource source = new Source();
            source.setName(textFieldSource.getText());
            recipe.setSource(source);

            ISeason season = new Season();
            season.setName(textFieldSaison.getText());
            recipe.setSeason(season);

            IDaytime daytime = new Daytime();
            daytime.setName(textFieldDaytime.getText());
            recipe.setDaytime(daytime);

            recipe.setText(textAreaZubereitungstext.getText());

            UI.changeRecipe(recipe);
            ControllerManageCookBooks.getInstance().refreshListViews();
            Stage stage = (Stage) changeButton.getScene().getWindow();
            stage.close();
        } else {
            manageSaveError("Sie haben die Plfichtfelder nicht ausgefüllt.", "Bitte füllen Sie die Pflichtfelder aus.");
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

    private int getZubereitungszeit(){
       String zubereitungszeit = textFieldZubereitungszeit.getText();
        int duration = 0;
        try {
            duration = Integer.parseInt(zubereitungszeit);
        }catch (NumberFormatException e){}
        return duration;
    }
}
