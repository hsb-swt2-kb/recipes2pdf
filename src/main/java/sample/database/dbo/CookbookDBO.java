package sample.database.dbo;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Many2Many;
import org.javalite.activejdbc.annotations.Table;
import sample.model.ICookbook;
import sample.model.IRecipe;
import sample.model.ISortlevel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by czoeller on 26.03.16.
 */
@Table("cookbook")
@Many2Many(other = CookbookDBO.class, join = "cookbook_recipe", sourceFKName = "recipe_id", targetFKName = "cookbook_id")
public class CookbookDBO extends Model implements ICookbook {

    static {
        validatePresenceOf("title");
    }

    @Override
    public Long getID() {
        return getLongId();
    }

    @Override
    public void setID(Long id) {
        setId(id);
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
    public List<ISortlevel> getSortlevel() {
        List<ISortlevel> list = new ArrayList<>();
        list.addAll(this.getAll(SortlevelDBO.class));
        Collections.reverse(list);
        return list;
    }

    @Override
    public void addSortlevel(ISortlevel sortlevel) {
        // Cast to underling database abstraction model
        this.add((Model) sortlevel);
    }

    @Override
    public List<IRecipe> getRecipes() {
        List<IRecipe> list = new ArrayList<>();
        list.addAll(this.getAll(RecipeDBO.class));
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
