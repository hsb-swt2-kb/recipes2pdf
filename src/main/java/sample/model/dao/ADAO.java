package sample.model.dao;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sample.model.IIdentifiable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * DAO Template to generify database actions.
 * Created by czoeller on 28.04.16.
 *
 * @param <POJO> A Plain Old Java Object that is mapped from the database.
 * @param <DBO>  A Database Object that is used to communicate with the database.
 */
abstract public class ADAO<POJO extends IIdentifiable, DBO extends Model & IIdentifiable> {

    final Logger LOG = LoggerFactory.getLogger(ADAO.class);

    /**
     * The generic type parameter of the DBO determined via reflection.
     */
    private Class<DBO> dboType;
    /**
     * An instance of the type parameter.
     */
    private DBO dbo;

    @SuppressWarnings("unchecked")
    public ADAO() {
        dboType = (Class<DBO>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        try {
            dbo = dboType.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            LOG.error("Passed type parameter are not compatible.", e);
        }
    }

    /**
     * Returns all records of this entity. If you need to get a subset, look for variations of "findFirst()".
     *
     * @return List of all records.
     */
    public List<POJO> getAll() {
        List<POJO> list = new ArrayList<>();
        LazyList<DBO> all = null;
        try {
            all = (LazyList<DBO>) MethodUtils.invokeMethod(dbo, "findAll");
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            LOG.error("Failed to get all records.", e);
        }
        if (all != null) {
            LOG.debug("Empty result for getAll.");
            list.addAll(all.stream().map(this::toPOJO).collect(Collectors.toList()));
        }
        return list;
    }

    /**
     * Returns an Optional record with given id.
     *
     * @param id The id of the record.
     * @return Optional<T> the optional record.
     */
    public Optional<POJO> findById(Long id) {
        DBO byId = null;
        try {
            byId = (DBO) MethodUtils.invokeMethod(dbo, "findById", id);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            LOG.error("Failed find record by id.", e);
        }
        if (null == byId) {
            LOG.debug("Empty result for findById with id = [" + id + "].");
            return Optional.empty();
        }
        final POJO pojo = toPOJO(byId);
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
            first = (DBO) MethodUtils.invokeMethod(dbo, "findFirst", subQuery, params);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }

        if (null == first) {
            LOG.debug("Empty result for findFirst with subQuery = [" + subQuery + "], params = [" + params + "].");
            return Optional.empty();
        }
        return Optional.of(toPOJO(first));

    }

    /**
     * Insert POJO into database.
     * ID has to be null.
     *
     * @param pojo POJO to insert ("id" attribute is modified by this method with insert id from db. )
     * @return true if the model was saved, false if you set an ID value for the model, but such ID does not exist in DB.
     */
    public boolean insert(POJO pojo) {
        if (null != pojo.getID()) {
            throw new IllegalStateException("A new DBO has no id!");
        }
        final DBO dbo = toDBO(pojo);
        boolean status = dbo.saveIt();
        pojo.setID(dbo.getID());
        return status;
    }

    /**
     * Update existing mapped POJO in database.
     * ID hast to be not null.
     *
     * @param pojo POJO to update.
     * @return true if the model was saved, false if you set an ID value for the model, but such ID does not exist in DB.
     */
    public boolean update(POJO pojo) {
        if (null == pojo.getID()) {
            throw new IllegalStateException("You must provide an id for update!");
        }
        final DBO dbo = toDBO(pojo);
        boolean status = dbo.saveIt();
        return status;
    }

    /**
     * Delete existing mapped POJO from database.
     *
     * @param pojo POJO to delete.
     * @return true if a record was deleted, false if not.
     */
    public boolean delete(POJO pojo) {
        if (null == pojo.getID()) {
            throw new IllegalStateException("You must provide an id to delete!");
        }
        final DBO dbo = toDBO(pojo);
        boolean status = dbo.delete();
        return status;
    }

    /**
     * Maps DBO to POJO
     *
     * @param dbo database object
     * @return the resulting pojo.
     */
    abstract POJO toPOJO(DBO dbo);

    /**
     * Maps POJO to DBO
     *
     * @param pojo plain old java object.
     * @return the resulting DBO.
     */
    abstract DBO toDBO(POJO pojo);
}
