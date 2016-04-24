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
@Many2Many(other = Cookbook.class, join = "cookbook_recipe", sourceFKName = "recipe_id", targetFKName = "cookbook_id")
@BelongsToParents({
    @BelongsTo(foreignKeyName = "category_id", parent = Category.class),
    @BelongsTo(foreignKeyName = "course_id", parent = Course.class),
    @BelongsTo(foreignKeyName = "region_id", parent = Region.class),
    @BelongsTo(foreignKeyName = "daytime_id", parent = Daytime.class),
    @BelongsTo(foreignKeyName = "season_id", parent = Season.class),
    @BelongsTo(foreignKeyName = "nurture_id", parent = Nurture.class)
})
public class Recipe extends Model implements IRecipe {

    static {
        validatePresenceOf("title");
    }

    @Override
    public long getID() {
        return this.getLongId();
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
        return parent(Category.class);
    }

    @Override
    public void setCourse(ICourse course) {
        ((Model) course).add(this);
    }

    @Override
    public ICourse getCourse() {
        return parent(Course.class);
    }

    @Override
    public void setRegion(IRegion region) {
        ((Model) region).add(this);
    }

    @Override
    public IRegion getRegion() {
        return parent(Region.class);
    }

    @Override
    public void setDaytime(IDaytime daytime) {
        ((Model) daytime).add(this);
    }

    @Override
    public IDaytime getDaytime() {
        return parent(Daytime.class);
    }

    @Override
    public void setSeason(ISeason season) {
        ((Model) season).add(this);
    }

    @Override
    public ISeason getSeason() {
        return parent(Season.class);
    }

    @Override
    public void setNurture(INurture nurture) {
        ((Model) nurture).add(this);
    }

    @Override
    public INurture getNurture() {
        return parent(Nurture.class);
    }

    @Override
    public void add(IRecipeIngredient recipeIngredient) {
        this.add((Model) recipeIngredient);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(String ingredientName, int amount, String unitName) {
        IIngredientRepository ingredientRepository = new IngredientRepository();
        IUnitRepository unitRepository = new UnitRepository();

        Optional<IIngredient> ingredient = ingredientRepository.findFirst("name = ?", ingredientName);
        Optional<IUnit> unit = unitRepository.findFirst("name = ?", unitName);

        // Create Many2Many Relation Recipe<---recipeIngredient--->Ingredient
        final RecipeIngredient recipeIngredient = RecipeIngredient.createIt("amount", amount);
        // Create HasMany Relation unit ---< recipe_ingredient
        // Create unit on the fly if it was not there yet
        unit.orElseGet(() -> (IUnit) Unit.createIt("name", unitName)).add(recipeIngredient);

        // set both recipeIngredient ends
        // Create ingredient on the fly if it was not there yet
        ingredient.orElseGet(() -> (IIngredient) Ingredient.createIt("name", ingredientName)).add(recipeIngredient);
        this.add((Model) recipeIngredient);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<IIngredient, Map<Integer, IUnit>> getIngredients() {
        Map<IIngredient, Map<Integer, IUnit>> map = new TreeMap<>();
        Map<Integer, IUnit> innerMap = new TreeMap<>();

        final List<RecipeIngredient> recipeIngredients = this.getAll(RecipeIngredient.class);

        for( RecipeIngredient recipeIngredient : recipeIngredients ) {
            final IIngredient ingredient = recipeIngredient.parent(Ingredient.class);
            final Integer amount = recipeIngredient.getInteger("amount");
            final IUnit unit = recipeIngredient.parent(Unit.class);
            innerMap.put(amount, unit);
            map.put(ingredient, innerMap);
        }

        return map;
    }

}
