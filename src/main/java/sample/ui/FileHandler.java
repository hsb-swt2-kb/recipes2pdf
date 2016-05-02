package sample.ui;

/**
 * Created by Tobias on 02.05.2016.
 */

import java.io.File;
import java.util.List;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class FileHandler {

    private Stage stage;
    private List<File> files;

    void importFiles(){
       Stage stage = new Stage();
       stage.setTitle("File Chooser Sample");
        final FileChooser fileChooser = new FileChooser();
        //FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("*.stl", "*.STL");
        //fileChooser.setSelectedExtensionFilter(extFilter);
        this.files =
            fileChooser.showOpenMultipleDialog(stage);
    }
}
