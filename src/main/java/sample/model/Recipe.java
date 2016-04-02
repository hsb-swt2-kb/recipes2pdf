package sample.model;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

/**
 * Created by czoeller on 25.03.16.
 */
@Table("recipe")
public class Recipe extends Model implements IRecipe {

    static {
        validatePresenceOf("title");
    }

    public void setTitle(String title){
        set("title", title);
    }

    @Override
    public void add(IRecipeIngredient recipeIngredient) {
        this.add((Model) recipeIngredient);
    }

    public String getTitle() {
        return getString("title");
    }

}
