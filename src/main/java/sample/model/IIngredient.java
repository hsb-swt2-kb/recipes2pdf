package sample.model;

/**
 * Created by czoeller on 16.04.16.
 */
public interface IIngredient extends Comparable<IIngredient> {
    String getName();

    void setName(String name);

    long getID();

    boolean saveIt();

    boolean delete();
}
