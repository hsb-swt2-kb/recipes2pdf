package sample.model;

import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Created by czoeller on 28.04.16.
 */
abstract public class ADAO<POJO extends Identity, DBO extends Model & Identity> {


    private Class<DBO> type;
    private DBO dbo;

    @SuppressWarnings("unchecked")
    public ADAO() {
        type = (Class<DBO>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        try {
            dbo = type.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    /**
     * Returns all records of this entity. If you need to get a subset, look for variations of "find()".
     *
     * @return result list
     */
    public List<POJO> getAll() {
        List<POJO> list = new ArrayList<>();
        LazyList<DBO> all = null;
        try {
            all = (LazyList<DBO>) type.getMethod("findAll", null).invoke(dbo);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

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

    public Optional<POJO> findById(Long id) {
        DBO byId = null;
        try {
            byId = (DBO) type.getMethod("findById", new Class[]{Object.class}).invoke(dbo, id);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
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

        DBO first = null;
        try {
            first = (DBO) type.getMethod("findFirst", new Class[]{String.class, Object[].class}).invoke(dbo, subQuery, params);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        if ( null == first ) {
            Optional.empty();
        }
        return Optional.of( toPOJO( first ) );

    }

    /**
     * ID has to be null for insert.
     *
     * @param pojo
     */
    public boolean insert(POJO pojo) {
        if( null != pojo.getID() ) {
            throw new IllegalStateException("A new DBO has no id!");
        }
        final DBO dbo = toDBO(pojo);
        boolean status = dbo.saveIt();
        pojo.setID( dbo.getID() );
        return status;
    }

    public boolean update(POJO pojo) {
        if( null == pojo.getID() ) {
            throw new IllegalStateException("You must provide an id for update!");
        }
        final DBO dbo = toDBO(pojo);
        boolean status = dbo.saveIt();
        return status;
    }

    public boolean delete(POJO pojo) {
        if( null == pojo.getID() ) {
            throw new IllegalStateException("You must provide an id to delete!");
        }
        final DBO dbo = toDBO(pojo);
        boolean status = dbo.delete();
        return status;
    }

    abstract POJO toPOJO(DBO dbo);
    abstract DBO toDBO(POJO pojo);
}
