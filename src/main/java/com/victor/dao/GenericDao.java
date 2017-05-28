package com.victor.dao;

import com.victor.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<T> {

    T findById(int id);

    T create(T entity);

    boolean delete(T entity);

    T update(T entity);

    List<T> findAll();

}
