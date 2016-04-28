package sample.model;

import java.util.List;
import java.util.Optional;


/**
 * Created by czoeller on 28.04.16.
 */
public interface IDAO<IPOJO> {
    /**
     * Returns all records of this entity. If you need to get a subset, look for variations of "find()".
     *
     * @return result list
     */
    List<IPOJO> getAll();

    /**
     * Returns an Optional record with given id.
     *
     * @param id
     * @return Optional<T> the optional record.
     */

    Optional<IPOJO> findById(long id);

    /**
     * Find first occurrence of record that fulfills the query.
     *
     * @param subQuery this is a set of conditions that normally follow the "where" clause. Example:
     *                 <code>"department = ? and dob &gt ?"</code>.
     * @param params   list of parameters corresponding to the place holders in the subquery.
     * @return instance of <code>List</code> containing results.
     */
    Optional<IPOJO> findFirst(String subQuery, Object... params);

    /**
     * ID of recipe has to be null for insert.
     */
    public void insert(IPOJO pojo);
    public void update(IPOJO pojo);
    public void delete(IPOJO pojo);
}
