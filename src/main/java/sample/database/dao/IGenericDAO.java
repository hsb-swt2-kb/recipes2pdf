package sample.database.dao;

import java.util.List;
import java.util.Optional;

/**
 * Created by czoeller on 11.07.16.
 */
public interface IGenericDAO<E,K> {
    void add(E entity) ;
    void saveOrUpdate(E entity) ;
    void update(E entity) ;
    void remove(E entity);
    E find(K key);
    List<E> getAll() ;
    Optional<E> findFirst(String field, Object value);
    E findOrCreate(E entity, String field, Object value);
    E findOrCreate(E entity);
    E findOrCreate_old(String field, Object value);
}
