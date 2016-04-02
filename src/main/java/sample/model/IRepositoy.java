package sample.model;

import java.util.List;
import java.util.Optional;

/**
 * Created by czoeller on 02.04.16.
 */
public interface IRepositoy<T> {
    List<T> getAll();
    Optional<T> findById(long id);
    Optional<T> findFirst(String subQuery, Object... params);
}
