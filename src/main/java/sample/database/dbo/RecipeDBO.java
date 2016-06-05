package sample.database.dbo;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.BelongsToParents;
import org.javalite.activejdbc.annotations.Many2Many;
import org.javalite.activejdbc.annotations.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sample.database.dao.IngredientDAO;
import sample.database.dao.UnitDAO;
import sample.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    @BelongsTo(foreignKeyName = "nurture_id", parent = NurtureDBO.class),
    @BelongsTo(foreignKeyName = "source_id", parent = SourceDBO.class)
})
public class RecipeDBO extends Model implements IRecipe {

    static {
        validatePresenceOf("title");
    }

    final Logger LOG = LoggerFactory.getLogger(RecipeDBO.class);

    @Override
    public Long getID() {
        return this.getLongId();
    }

    @Override
    public void setID(Long id) {
        setId(id);
    }

    public String getTitle() {
        return getString("title");
    }

    public void setTitle(String title) {
        setString("title", title);
    }

    @Override
    public String getText() {
        return getString("text");
    }

    @Override
    public void setText(String text) {
        setString("text", text);
    }

    @Override
    public int getPortions() {
        return getInteger("portions");
    }

    @Override
    public void setPortions(int portions) {
        setInteger("portions", portions);
    }

    @Override
    public int getDuration() {
        return getInteger("duration");
    }

    @Override
    public void setDuration(int duration) {
        setInteger("duration", duration);
    }

    public byte[] getImage() {
        return getBytes("image");
    }

    public void setImage(byte[] image) {
        set("image", image);
    }

    @Override
    public int getCalories() {
        return getInteger("calories");
    }

    @Override
    public void setCalories(int calories) {
        setInteger("calories", calories);
    }

    @Override
    public ICategory getCategory() {
        return parent(CategoryDBO.class);
    }

    @Override
    public void setCategory(ICategory category) {
        ((Model) category).add(this);
    }

    @Override
    public ICourse getCourse() {
        return parent(CourseDBO.class);
    }

    @Override
    public void setCourse(ICourse course) {
        ((Model) course).add(this);
    }

    @Override
    public IRegion getRegion() {
        return parent(RegionDBO.class);
    }

    @Override
    public void setRegion(IRegion region) {
        ((Model) region).add(this);
    }

    @Override
    public IDaytime getDaytime() {
        return parent(DaytimeDBO.class);
    }

    @Override
    public void setDaytime(IDaytime daytime) {
        ((Model) daytime).add(this);
    }

    @Override
    public ISeason getSeason() {
        return parent(SeasonDBO.class);
    }

    @Override
    public void setSeason(ISeason season) {
        ((Model) season).add(this);
    }

    @Override
    public INurture getNurture() {
        return parent(NurtureDBO.class);
    }

    @Override
    public void setNurture(INurture nurture) {
        ((Model) nurture).add(this);
    }

    @Override
    public ISource getSource() {
        return parent(SourceDBO.class);
    }

    @Override
    public void setSource(ISource source) {
        ((Model) source).add(this);
    }

    /**
     * {@inheritDoc}
     */
    public void add(String ingredientName, double amount, String unitName) {
        final IngredientDAO ingredientDAO = new IngredientDAO();
        final UnitDAO unitDAO = new UnitDAO();

        final Optional<Ingredient> ingredient = ingredientDAO.findFirst("name = ?", ingredientName);
        final Optional<Unit> unit = unitDAO.findFirst("name = ?", unitName);

        // Create Many2Many Relation RecipeDBO<---recipeIngredient--->IngredientDBO
        final RecipeIngredientDBO recipeIngredient = RecipeIngredientDBO.createIt("amount", amount);


        UnitDBO unitDBO;
        IngredientDBO ingredientDBO;
        // Create HasMany Relation unit ---< recipe_ingredient
        // Create unit on the fly if it was not there yet
        if (!unit.isPresent()) {
            Unit newUnit = new Unit();
            newUnit.setName(unitName);
            unitDAO.insert(newUnit);
            unitDBO = unitDAO.toDBO(newUnit);
            unitDBO.add(recipeIngredient);
            LOG.info("Added new unit: unitName = [" + unitName + "]");
        } else {
            unitDBO = unitDAO.toDBO(unit.get());
            unitDBO.add(recipeIngredient);
        }

        // set both recipeIngredient ends
        // Create ingredient on the fly if it was not there yet
        if (!ingredient.isPresent()) {
            Ingredient newIngredient = new Ingredient();
            newIngredient.setName(ingredientName);
            ingredientDAO.insert(newIngredient);
            ingredientDBO = ingredientDAO.toDBO(newIngredient);
            ingredientDBO.add(recipeIngredient);
            LOG.info("Added new ingredient: ingredientName = [" + ingredientName + "]");
        } else {
            ingredientDBO = ingredientDAO.toDBO(ingredient.get());
            ingredientDBO.add(recipeIngredient);
        }

        final boolean alreadyPersisted = 0 < RecipeIngredientDBO.count("recipe_id = ? AND ingredient_id = ?", this.getID(), ingredientDBO.getID());
        if (!alreadyPersisted) {
            this.add(recipeIngredient);
            LOG.info("Associated recipe and ingredient: ingredientName = [" + ingredientName + "], amount = [" + amount + "], unitName = [" + unitName + "]");
        } else {
            recipeIngredient.delete();
            LOG.info("Rejected association between recipe and ingredient as already present: ingredientName = [" + ingredientName + "], amount = [" + amount + "], unitName = [" + unitName + "] (already exists)");
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Triple<IIngredient, Double, IUnit>> getIngredients() {
        List<Triple<IIngredient, Double, IUnit>> ingredients = new ArrayList<>();

        final List<RecipeIngredientDBO> recipeIngredients = this.getAll(RecipeIngredientDBO.class);

        for (RecipeIngredientDBO recipeIngredient : recipeIngredients) {
            final IIngredient ingredient = recipeIngredient.parent(IngredientDBO.class);
            final Double amount = recipeIngredient.getDouble("amount");
            final IUnit unit = recipeIngredient.parent(UnitDBO.class);
            ingredients.add(new ImmutableTriple<>(ingredient, amount, unit));
        }

        return ingredients;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void add(Triple<IIngredient, Double, IUnit> recipeIngredient) {
        this.add(recipeIngredient.getLeft().getName(), recipeIngredient.getMiddle(), recipeIngredient.getRight().getName());
    }

}
