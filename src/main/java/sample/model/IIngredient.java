package sample.model;

    import static org.apache.commons.lang3.ObjectUtils.compare;

/**
 * Created by czoeller on 02.04.16.
 */
public interface IIngredient extends IIdentifiable, Comparable<IIngredient> {
    String getName();
    void setName(String name);
    @Override
    default int compareTo(IIngredient o) {
        return compare(getName(), o.getName());
    }
}
