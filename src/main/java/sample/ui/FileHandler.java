package sample.ui;

/**
 * @author Tobias Stelter
 * The Class ''FileHandler'' provides methods for opening a filechooser.
 */


import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;


class FileHandler {

    /**
     * These method imports the data of a single folder.
     */
    File importFolder() {
        Stage stage = new Stage();
        stage.setTitle(Resources.getFileChooserWindowText());
        final DirectoryChooser chooser = new DirectoryChooser();
        return chooser.showDialog(stage);
    }

    /**
     * These method imports a single or multiple files.
     */
    List<File> importFiles(){
        Stage stage = new Stage();
        stage.setTitle(Resources.getFileChooserWindowText());
        final FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("*.txt", "*.TXT","*.html", "*.HTML");
        fileChooser.setSelectedExtensionFilter(extFilter);
        return  fileChooser.showOpenMultipleDialog(stage);
    }

    /**
     * These method imports a single file.
     */
    File importPicture(){
        Stage stage = new Stage();
        stage.setTitle(Resources.getFileChooserWindowText());
        final FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("*.PNG", "*.png","*.JPG","*.jpg");
        fileChooser.setSelectedExtensionFilter(extFilter);
        return fileChooser.showOpenDialog(stage);
    }

    /**
     * These method exports a single file.
     */
    @SuppressWarnings("unused")
    File exportFile(){
        Stage stage = new Stage();
        stage.setTitle(Resources.getFileChooserWindowText());
        final FileChooser fileChooser = new FileChooser();
        return fileChooser.showSaveDialog(stage);
    }
}
