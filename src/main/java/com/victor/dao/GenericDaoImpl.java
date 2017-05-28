package com.victor.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

/**
 * Created by vmuresanu on 4/5/2017.
 */
public abstract class GenericDaoImpl<PK extends Serializable, T> {

    private final Class< T > persistentClass;

    @SuppressWarnings("Unchecked")
    public GenericDaoImpl(){
        this.persistentClass = (Class<T>)((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    @Autowired
    private SessionFactory sessionFactory;

    protected Session getSession(){
        return sessionFactory.getCurrentSession();
    }

    @SuppressWarnings("unchecked")
    public T getByKey(PK key){
        return (T) getSession().get(persistentClass, key);
    }

    public void persist(T entity){
        getSession().persist(entity);
    }

    public void delete(T entity){
        getSession().delete(entity);
    }

    public void update(T entity){
        getSession().update(entity);
    }

    protected Criteria createEntityCriteria(){
        return getSession().createCriteria(persistentClass);
    }
}
