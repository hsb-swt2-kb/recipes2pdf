package sample.ui;

/**
 * Created by Tobias on 14.05.2016.
 * The Class ''Resources'' stores the FXML-paths, the icon-paths and the window-titles.
 */
public class Resources {

    //FXMLs
    static String getDefaultFXML() {return "/sample/ui/Default.fxml";}
    static String getErrorFXML() {
        return "/sample/ui/Error.fxml";
    }
    static String getMangeCookBookFXML() {
        return "/sample/ui/ManageCookBook.fxml";
    }
    static String getMangeCookBooksFXML() {
        return "/sample/ui/ManageCookBooks.fxml";
    }
    static String getChangeCookBooksFXML() {
        return "/sample/ui/ChangeCookBook.fxml";
    }
    static String getAddCookBookFXML() {
        return "/sample/ui/AddCookBook.fxml";
    }
    static String getDeleteCookBookFXML() {
        return "/sample/ui/DeleteDialogCookBook.fxml";
    }
    static String getDeleteRecipeFXML() {
        return "/sample/ui/DeleteDialogRecipe.fxml";
    }
    static String getloadRecipeFXML() {
        return "/sample/ui/LoadRecipe.fxml";
    }
    static String getHelpFXML() {
        return "/sample/ui/Help.fxml";
    }
    static String getAboutFXML() {
        return "/sample/ui/About.fxml";
    }
    static String getExportFXML() {
        return "/sample/ui/ExportCookBook.fxml";
    }
    static String getSortLevelFXML() {
        return "/sample/ui/SortLevel.fxml";
    }
    static String getChangeRecipeFXML() {return "/sample/ui/ChangeRecipe.fxml";}

    //Icons
    static String getDefaultIcon() { return "/sample/ui/icon_bg_small.png"; }
    static String getHelpIcon() {
        return "/sample/ui/Help.png";
    }
    static String getInfoIcon() {
        return "/sample/ui/Info.png";
    }

    //Window-Titles
    static String getFileChooserWindowText() {
        return "File Chooser";
    }
    static String getDeleteWindowText() {
        return "Löschen";
    }
    static String getSortLevelWindowText() {return "Einstellungen";}
    static String getChangeCookBookWindowText() {return "Kochbuch ändern";}
    static String getErrorWindowText() {
        return "Error";
    }
    static String getAddCookBookWindowText() {return "Kochbuch hinzufügen";}
    static String getExportWindowText() {return "Exportieren";}
    static String getChangeRecipeWindowText() {
        return "Rezept ändern";
    }
    static String getLoadWindowText() { return "Rezept laden"; }
    static String getHelpWindowText() { return "Hilfe"; }
    static String getAboutWindowText() { return "Über";}
    static String getManageCookBooksWindowText() {return "Kochbuchverwaltung";}
}
