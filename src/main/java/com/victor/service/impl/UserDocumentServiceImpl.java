package com.victor.service.impl;

import com.victor.dao.UserDocumentDao;
import com.victor.model.UserDocument;
import com.victor.service.UserDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by vmuresanu on 5/6/2017.
 */
@Service("userDocumentService")
@Transactional
public class UserDocumentServiceImpl implements UserDocumentService{

    @Autowired
    UserDocumentDao dao;

    @Override
    public List<UserDocument> findAll() {
        return dao.findAll();
    }

    @Override
    public UserDocument findById(int id) {
        return dao.findById(id);
    }

    @Override
    public void saveDocument(UserDocument document) {
        dao.save(document);
    }

    @Override
    public List<UserDocument> findAllByUserId(int userId) {
        return dao.findAllByUserId(userId);
    }

    @Override
    public void deleteById(int id) {
        dao.deleteById(id);
    }
}
