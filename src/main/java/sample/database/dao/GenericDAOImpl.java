package sample.database.dao;

import com.google.inject.TypeLiteral;
import com.uaihebert.factory.EasyCriteriaFactory;
import com.uaihebert.model.EasyCriteria;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

/**
 * Created by czoeller on 11.07.16.
 */
@SuppressWarnings("unchecked")
public class GenericDAOImpl<E, ID extends Serializable> implements IGenericDAO<E, ID> {

    @Inject
    private EntityManager em;
    protected Class<? extends ID> daoType;

    @Inject
    @SuppressWarnings("unchecked")
    public GenericDAOImpl(TypeLiteral<E> type) {
        this.daoType = (Class<? extends ID>) type.getRawType();
    }

    public GenericDAOImpl() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        daoType = (Class) pt.getActualTypeArguments()[0];
    }

    @Override
    public void add(E entity) {
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
    }

    @Override
    public void saveOrUpdate(E entity) {
        em.getTransaction().begin();
        em.merge(entity);
        em.getTransaction().commit();
    }

    @Override
    public void update(E entity) {
        em.getTransaction().begin();
        em.merge(entity);
        em.getTransaction().commit();
    }

    @Override
    public void remove(E entity) {
        em.getTransaction().begin();
        em.remove(entity);
        em.getTransaction().commit();
    }

    @Override
    public E find(ID key) {
        em.getTransaction().begin();
        final E e = (E) em.find(daoType, key);
        em.getTransaction().commit();
        return e;
    }

    @Override
    public List<E> getAll() {
        return em.createQuery("Select t from " + daoType.getSimpleName() + " t").getResultList();
    }

    @Override
    public Optional<E> findFirst(String field, String value) {
        EasyCriteria<? extends ID> easyCriteria = EasyCriteriaFactory.createQueryCriteria(em, daoType);
        try {
            return Optional.ofNullable((E) easyCriteria.andStringLike(field,value).getSingleResult() );
        }
        catch(NoResultException | EntityNotFoundException nre) {
            return Optional.empty();
        }
    }

    @Override
    public E findOrCreate(E entity, String field, String value) {
        final Optional<E> entitySearch = findFirst(field, value);
        if( !entitySearch.isPresent() ) {
            add( entity );
            return entity;
        } else {
            return entitySearch.get();
        }
    }

    @Override
    public E findOrCreate(E entity) {
        if ( !em.contains(entity) ) {
            add(entity);
        }
        return entity;
    }
}
