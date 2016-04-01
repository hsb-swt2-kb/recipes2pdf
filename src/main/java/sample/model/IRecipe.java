package sample.model;

/**
 * Created by czoeller on 01.04.16.
 */
public interface IRecipe {
    String getTitle();
    void setTitle(String title);
    boolean saveIt();
    void add(ICookbook cookbook);
    void remove(ICookbook cookbook);
}
