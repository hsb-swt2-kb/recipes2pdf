package sample.pdfBuilder;

import java.util.ArrayList;

/**
 * Created by kai on 31.03.16.
 */

public class Recipe {
    public ArrayList<Ingredient> getIngredients() {
        ArrayList<Ingredient> ingredientlist = new ArrayList<>();
        ingredientlist.add(new Ingredient());
        ingredientlist.add(new Ingredient());
        return ingredientlist;
    }

    public String getCategory() {
        return "Haupgericht";
    }

    public String getRecipeType() {
        return "Nudelgericht";
    }

    public String getCategoryNumber() {
        return "2";
    }

    public String getRecipeTypeNumber() {
        return "1";
    }

    public int getID() {
        return 1;
    }

    public String getRecipeText() {
        return "'N bisschen hiervon, 'n bisschen davon, rühren, backofen, fertig! Und wenn alle Stricke reissen, einfach zur nächsten Imbissbude und 'einmal swei halbe Hahn' bestellen";
    }
}
