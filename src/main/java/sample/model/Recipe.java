package sample.model;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by czoeller on 28.04.16.
 */
public class Recipe implements IRecipe {

    private Long id;
    private String title;
    private String text;
    private int portions;
    private int duration;
    private byte[] image;
    private int calories;
    private ICategory category;
    private ICourse course;
    private IRegion region;
    private IDaytime daytime;
    private ISeason season;
    private INurture nurture;
    private ISource source;
    private List<Triple<IIngredient, Double, IUnit>> ingredients = new ArrayList<>(10);

    @Override
    public Long getID() {
        return this.id;
    }

    @Override
    public void setID(Long id) {
        this.id = id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public int getPortions() {
        return portions;
    }

    @Override
    public void setPortions(int portions) {
        this.portions = portions;
    }

    @Override
    public int getDuration() {
        return duration;
    }

    @Override
    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public byte[] getImage() {
        return image;
    }

    @Override
    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public int getCalories() {
        return calories;
    }

    @Override
    public void setCalories(int calories) {
        this.calories = calories;
    }

    @Override
    public ICategory getCategory() {
        return category;
    }

    @Override
    public void setCategory(ICategory category) {
        this.category = category;
    }

    @Override
    public ICourse getCourse() {
        return course;
    }

    @Override
    public void setCourse(ICourse course) {
        this.course = course;
    }

    @Override
    public IRegion getRegion() {
        return region;
    }

    @Override
    public void setRegion(IRegion region) {
        this.region = region;
    }

    @Override
    public IDaytime getDaytime() {
        return daytime;
    }

    @Override
    public void setDaytime(IDaytime daytime) {
        this.daytime = daytime;
    }

    @Override
    public ISeason getSeason() {
        return season;
    }

    @Override
    public void setSeason(ISeason season) {
        this.season = season;
    }

    @Override
    public INurture getNurture() {
        return nurture;
    }

    @Override
    public void setNurture(INurture nurture) {
        this.nurture = nurture;
    }

    @Override
    public ISource getSource() {
        return source;
    }

    @Override
    public void setSource(ISource source) {
        this.source = source;
    }

    /**
     * {@inheritDoc}
     */
    public void add(String ingredientName, double amount, String unitName) {
        IIngredient ingredient = new Ingredient();
        ingredient.setName(ingredientName);

        IUnit unit = new Unit();
        unit.setName(unitName);

        this.ingredients.add(new ImmutableTriple<>(ingredient, amount, unit));
    }

    public boolean isEmpty(){
        if (this.title.isEmpty() && this.text.isEmpty() && this.ingredients.isEmpty())
            return true;
        else
            return false;
    }

    public boolean isIncomplete(){
        if (this.title.isEmpty() || this.text.isEmpty() || this.ingredients.isEmpty())
            return true;
        else
            return false;
    }

    /**
     * {@inheritDoc}
     */
    public List<Triple<IIngredient, Double, IUnit>> getIngredients() {
        return this.ingredients;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(Triple<IIngredient, Double, IUnit> recipeIngredient) {
        this.ingredients.add(recipeIngredient);
    }

}
