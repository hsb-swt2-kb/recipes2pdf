package sample.model;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

import java.util.List;

/**
 * Created by czoeller on 26.03.16.
 */
@Table("cookbook")
public class Cookbook extends Model implements ICookbook {

    static {
        validatePresenceOf("title");
    }

    @Override
    public String getTitle() {
        return getString("title");
    }

    @Override
    public void setTitle(String title) {
        setString("title", title);
    }

    @Override
    public List<Recipe> getRecipes() {
        return this.getAll(Recipe.class);
    }

    @Override
    public void addRecipe(IRecipe recipe) {
        // Cast to underling database abstraction model
        this.add((Model) recipe);
        recipe.add(this);
    }

    @Override
    public void removeRecipe(IRecipe recipe) {
        // Cast to underling database abstraction model
        this.remove((Model) recipe);
        recipe.remove(this);
    }

}
