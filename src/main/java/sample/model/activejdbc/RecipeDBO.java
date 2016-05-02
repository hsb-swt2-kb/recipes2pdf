package sample.model.activejdbc;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.BelongsToParents;
import org.javalite.activejdbc.annotations.Many2Many;
import org.javalite.activejdbc.annotations.Table;
import sample.model.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

/**
 * Created by czoeller on 25.03.16.
 */
@Table("recipe")
@Many2Many(other = CookbookDBO.class, join = "cookbook_recipe", sourceFKName = "recipe_id", targetFKName = "cookbook_id")
@BelongsToParents({
    @BelongsTo(foreignKeyName = "category_id", parent = CategoryDBO.class),
    @BelongsTo(foreignKeyName = "course_id", parent = CourseDBO.class),
    @BelongsTo(foreignKeyName = "region_id", parent = RegionDBO.class),
    @BelongsTo(foreignKeyName = "daytime_id", parent = DaytimeDBO.class),
    @BelongsTo(foreignKeyName = "season_id", parent = SeasonDBO.class),
    @BelongsTo(foreignKeyName = "nurture_id", parent = NurtureDBO.class)
})
public class RecipeDBO extends Model implements IRecipe, Identity {

    @Override
    public Long getID() {
        return this.getLongId();
    }

    @Override
    public void setID(Long id) {
        setId(id);
    }

    public void setTitle(String title) {
        setString("title", title);
    }

    public String getTitle() {
        return getString("title");
    }

    @Override
    public void setText(String text) {
        setString("text", text);
    }

    @Override
    public String getText() {
        return getString("text");
    }

    @Override
    public void setPortions(int portions) {
        setInteger("portions", portions);
    }

    @Override
    public int getPortions() {
        return getInteger("portions");
    }

    @Override
    public void setDuration(int duration) {
        setInteger("duration", duration);
    }

    @Override
    public int getDuration() {
        return getInteger("duration");
    }

    public void setImage(byte[] image) {
        set("image", image);
    }

    public byte[] getImage() {
        return getBytes("image");
    }

    @Override
    public void setCalories(int calories) {
        setInteger("calories", calories);
    }

    @Override
    public int getCalories() {
        return getInteger("calories");
    }

    @Override
    public void setCategory(ICategory category) {
        ((Model) category).add(this);
    }

    @Override
    public ICategory getCategory() {
        return parent(CategoryDBO.class);
    }

    @Override
    public void setCourse(ICourse course) {
        ((Model) course).add(this);
    }

    @Override
    public ICourse getCourse() {
        return parent(CourseDBO.class);
    }

    @Override
    public void setRegion(IRegion region) {
        ((Model) region).add(this);
    }

    @Override
    public IRegion getRegion() {
        return parent(RegionDBO.class);
    }

    @Override
    public void setDaytime(IDaytime daytime) {
        ((Model) daytime).add(this);
    }

    @Override
    public IDaytime getDaytime() {
        return parent(DaytimeDBO.class);
    }

    @Override
    public void setSeason(ISeason season) {
        ((Model) season).add(this);
    }

    @Override
    public ISeason getSeason() {
        return parent(SeasonDBO.class);
    }

    @Override
    public void setNurture(INurture nurture) {
        ((Model) nurture).add(this);
    }

    @Override
    public INurture getNurture() {
        return parent(NurtureDBO.class);
    }

   /* @Override
    public void add(String ingredientName, int amount, String unitName) {
        throw new IllegalStateException("Unimplemented");
    }*/

    /**
     * {@inheritDoc}
     */
    public void add(String ingredientName, int amount, String unitName) {
        final IngredientDAO ingredientDAO = new IngredientDAO();
        final UnitDAO unitDAO = new UnitDAO();

        final Optional<Ingredient> ingredient = ingredientDAO.findFirst("name = ?", ingredientName);
        final Optional<Unit> unit = unitDAO.findFirst("name = ?", unitName);

        // Create Many2Many Relation RecipeDBO<---recipeIngredient--->IngredientDBO
        final RecipeIngredientDBO recipeIngredient = RecipeIngredientDBO.createIt("amount", amount);
        // Create HasMany Relation unit ---< recipe_ingredient
        // Create unit on the fly if it was not there yet
        if( ! unit.isPresent() ) {
            Unit newUnit = new Unit();
            newUnit.setName(unitName);
            unitDAO.insert(newUnit);
            unitDAO.toDBO(newUnit).add(recipeIngredient);
        } else {
            final UnitDBO unitDBO = unitDAO.toDBO(unit.get());
            unitDBO.add(recipeIngredient);
        }

        // set both recipeIngredient ends
        // Create ingredient on the fly if it was not there yet
        if( ! ingredient.isPresent() ) {
            Ingredient newIngredient = new Ingredient();
            newIngredient.setName(unitName);
            ingredientDAO.insert(newIngredient);
            ingredientDAO.toDBO(newIngredient).add(recipeIngredient);
        } else {
            final IngredientDBO ingredientDBO = ingredientDAO.toDBO(ingredient.get());
            ingredientDBO.add(recipeIngredient);
        }
        this.add(recipeIngredient);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<IIngredient, Map<Integer, IUnit>> getIngredients() {
        Map<IIngredient, Map<Integer, IUnit>> map = new TreeMap<>();
        Map<Integer, IUnit> innerMap = new TreeMap<>();

        final List<RecipeIngredientDBO> recipeIngredients = this.getAll(RecipeIngredientDBO.class);

        for (RecipeIngredientDBO recipeIngredient : recipeIngredients) {
            final IIngredient ingredient = recipeIngredient.parent(IngredientDBO.class);
            final Integer amount = recipeIngredient.getInteger("amount");
            final IUnit unit = recipeIngredient.parent(UnitDBO.class);
            innerMap.put(amount, unit);
            map.put(ingredient, innerMap);
        }

        return map;
    }

}
