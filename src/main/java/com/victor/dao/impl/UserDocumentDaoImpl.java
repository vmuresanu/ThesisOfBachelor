package com.victor.dao.impl;

import com.victor.dao.GenericDaoImpl;
import com.victor.dao.UserDocumentDao;
import com.victor.model.UserDocument;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userDocumentDao")
public class UserDocumentDaoImpl extends GenericDaoImpl<Integer, UserDocument> implements UserDocumentDao{
    @SuppressWarnings("unchecked")
    @Override
    public List<UserDocument> findAll() {
        Criteria criteria = createEntityCriteria();
        return (List<UserDocument>)criteria.list();
    }

    @Override
    public UserDocument findById(int id) {
        return getByKey(id);
    }

    @Override
    public void save(UserDocument document) {
        persist(document);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UserDocument> findAllByUserId(int userId) {
        Criteria criteria = createEntityCriteria();
        Criteria userCriteria = criteria.createCriteria("user");
        userCriteria.add(Restrictions.eq("id", userId));
        return (List<UserDocument>)criteria.list();
    }

    @Override
    public void deleteById(int id) {
        UserDocument document = getByKey(id);
        delete(document);
    }
}
