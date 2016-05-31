package sample.model;

import org.apache.commons.lang3.tuple.Triple;

import java.util.List;

/**
 * Created by czoeller on 01.04.16.
 */
public interface IRecipe extends IIdentifiable {
    String getTitle();

    void setTitle(String title);

    void setText(String text);

    String getText();

    void setPortions(int portions);

    int getPortions();

    void setDuration(int duration);

    int getDuration();

    void setImage(byte[] image);

    byte[] getImage();

    void setCalories(int calories);

    int getCalories();

    void setCategory(ICategory category);

    ICategory getCategory();

    void setCourse(ICourse course);

    ICourse getCourse();

    void setRegion(IRegion region);

    IRegion getRegion();

    void setDaytime(IDaytime daytime);

    IDaytime getDaytime();

    void setSeason(ISeason season);

    ISeason getSeason();

    void setNurture(INurture nurture);

    INurture getNurture();

    ISource getSource();

    void setSource(ISource source);

    /**
     * Add ingredient with amount and unit.
     * This is a convenience method that creates missing entities on the fly.
     *
     * @param ingredientName the name of ingredient
     * @param amount         amount of ingredient
     * @param unitName       name of the unit
     */
    void add(String ingredientName, double amount, String unitName);

    /**
     * Retrieve List of Triples with details <IIngredient, Double, IUnit>.
     *
     * @return the list of triples
     */
    List<Triple<IIngredient, Double, IUnit>> getIngredients();

    /**
     * Insert recipe ingredient directly.
     * In most cases you should use <code>add(String ingredientName, int amount, String unitName)</code>
     * @see #add(String, double, String)
     * @param recipeIngredient
     */
    void add(Triple<IIngredient, Double, IUnit> recipeIngredient);
}
