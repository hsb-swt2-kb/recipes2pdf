package sample.model;

import java.util.List;
import java.util.Optional;

/**
 * Created by czoeller on 02.04.16.
 */
public interface IRepositoy<T> {

    /**
     * Returns all records of this entity. If you need to get a subset, look for variations of "find()".
     *
     * @return result list
     */
    List<T> getAll();

    /**
     * Returns an Optional record with given id.
     *
     * @param id
     * @return Optional<T> the optional record.
     */

    Optional<T> findById(long id);

    /**
     * Find first occurrence of record that fulfills the query.
     *
     * @param subQuery this is a set of conditions that normally follow the "where" clause. Example:
     *                 <code>"department = ? and dob &gt ?"</code>.
     * @param params   list of parameters corresponding to the place holders in the subquery.
     * @return instance of <code>List</code> containing results.
     */
    Optional<T> findFirst(String subQuery, Object... params);
}
