package sample.model;

/**
 * Created by czoeller on 02.04.16.
 */
public interface IIngredient extends Comparable<IIngredient> {
    String getName();
    void setName(String name);
    long getID();
}
