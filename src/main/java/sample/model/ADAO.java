package sample.model;

import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;
import sample.model.activejdbc.RecipeDBO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Created by czoeller on 28.04.16.
 */
abstract public class ADAO<POJO, DBO extends Model> {
    /**
     * Returns all records of this entity. If you need to get a subset, look for variations of "find()".
     *
     * @return result list
     */
    public List<POJO> getAll() {
        List<POJO> list = new ArrayList<>();
        final LazyList<DBO> all = RecipeDBO.findAll();
        for( DBO dbo : all ) {
            list.add( toPOJO( dbo ) );
        }
        return list;
    }

    /**
     * Returns an Optional record with given id.
     *
     * @param id
     * @return Optional<T> the optional record.
     */

    public Optional<POJO> findById(long id) {
        final DBO byId = DBO.findById(id);
        if( null == byId ) {
            return Optional.empty();
        }
        final POJO pojo = toPOJO( byId );
        return Optional.ofNullable(pojo);
    }

    /**
     * Find first occurrence of record that fulfills the query.
     *
     * @param subQuery this is a set of conditions that normally follow the "where" clause. Example:
     *                 <code>"department = ? and dob &gt ?"</code>.
     * @param params   list of parameters corresponding to the place holders in the subquery.
     * @return instance of <code>List</code> containing results.
     */
    public Optional<POJO> findFirst(String subQuery, Object... params) {
        final DBO first = DBO.findFirst(subQuery, params);
        if ( null == first ) {
            Optional.empty();
        }
        return Optional.of( toPOJO( first ) );

    }

    /**
     * ID of recipe has to be null for insert.
     */
    abstract boolean insert(POJO pojo);
    abstract boolean update(POJO pojo);
    abstract boolean delete(POJO pojo);

    abstract POJO toPOJO(DBO dbo);
    abstract DBO toDBO(POJO pojo);
}
