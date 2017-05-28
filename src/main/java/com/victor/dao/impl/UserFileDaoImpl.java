package com.victor.dao.impl;

import com.victor.dao.GenericDaoImpl;
import com.victor.dao.UserFileDao;
import com.victor.model.UserFile;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by vmuresanu on 4/27/2017.
 */
@Repository("userFileDao")
public class UserFileDaoImpl extends GenericDaoImpl<Integer, UserFile> implements UserFileDao {

    @SuppressWarnings("unchecked")
    @Override
    public List<UserFile> findAll() {
        Criteria criteria = createEntityCriteria();
        return (List<UserFile>)criteria.list();
    }

    @Override
    public UserFile findById(int id) {
        return getByKey(id);
    }

    @Override
    public void save(UserFile document) {
        persist(document);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UserFile> findAllByUserId(int userId) {
        Criteria criteria = createEntityCriteria();
        Criteria userCriteria = criteria.createCriteria("user");
        userCriteria.add(Restrictions.eq("id", userId));
        return (List<UserFile>)criteria.list();
    }

    @Override
    public void deleteById(int id) {
        UserFile document = getByKey(id);
        delete(document);
    }
}
