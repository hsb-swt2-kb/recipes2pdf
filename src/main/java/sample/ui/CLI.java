package sample.ui;

import sample.model.ICookbook;

import java.io.IOException;
import java.util.ArrayList;

/**
 *  * Created by sabine on 21.05.16.
 *   */
public class CLI extends UI {
    // TODO: implement CLI

    public CLI(){}

    public void start(String[] args) {
        switch (args[0]) {
            case "add":     break;
            case "show":    break;
            case "edit":    break;
            case "remove":  break;
            case "make":    break;
            case "define":  break;
            default:        break;
        }
    }

    void insertRecipe(String[] args){}
    void showRecipe(String[] args){}
    void editRecipe(String[] args){}
    void removeRecipe(String[] args){}
    void makeCookBook(String[] args){}
    void defineCookbook(String[] args){}
}
