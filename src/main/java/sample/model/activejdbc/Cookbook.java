package sample.model.activejdbc;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;
import sample.model.ICookbook;
import sample.model.IRecipe;

import java.util.ArrayList;
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
    public List<IRecipe> getRecipes() {
        List<IRecipe> list = new ArrayList<>();
        list.addAll( this.getAll(Recipe.class) );
        return list;
    }

    @Override
    public void addRecipe(IRecipe recipe) {
        // Cast to underling database abstraction model
        this.add((Model) recipe);
    }

    @Override
    public void removeRecipe(IRecipe recipe) {
        // Cast to underling database abstraction model
        this.remove((Model) recipe);
    }

}
