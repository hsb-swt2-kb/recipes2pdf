package sample.database.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.inject.Inject;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by czoeller on 11.07.16.
 */
@SuppressWarnings("unchecked")
public class GenericDAOImpl<E, ID extends Serializable> implements IGenericDAO<E, ID> {

    @Inject
    private SessionFactory sessionFactory;
    protected Class<? extends ID> daoType;

    public GenericDAOImpl() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        daoType = (Class) pt.getActualTypeArguments()[0];
    }

    protected Session currentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void add(E entity) {
        currentSession().beginTransaction();
        currentSession().save(entity);
        currentSession().getTransaction().commit();
    }

    @Override
    public void saveOrUpdate(E entity) {
        currentSession().beginTransaction();
        currentSession().saveOrUpdate(entity);
        currentSession().getTransaction().commit();
    }

    @Override
    public void update(E entity) {
        currentSession().beginTransaction();
        currentSession().saveOrUpdate(entity);
        currentSession().getTransaction().commit();
    }

    @Override
    public void remove(E entity) {
        currentSession().beginTransaction();
        currentSession().delete(entity);
        currentSession().getTransaction().commit();
    }

    @Override
    public E find(ID key) {
        currentSession().beginTransaction();
        final E e = (E) currentSession().get(daoType, key);
        currentSession().getTransaction().commit();
        return e;
    }

    @Override
    public List<E> getAll() {
        currentSession().beginTransaction();
        final List list = currentSession().createCriteria(daoType).list();
        currentSession().getTransaction().commit();
        return list;
    }

}
