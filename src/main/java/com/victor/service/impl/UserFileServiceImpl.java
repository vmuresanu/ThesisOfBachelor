package com.victor.service.impl;

import com.victor.dao.UserFileDao;
import com.victor.model.UserFile;
import com.victor.service.UserFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by vmuresanu on 4/27/2017.
 */
@Service("userFileService")
@Transactional
public class UserFileServiceImpl implements UserFileService {

    @Autowired
    UserFileDao dao;

    @Override
    public List<UserFile> findAll() {
        return dao.findAll();
    }

    @Override
    public UserFile findById(int id) {
        return dao.findById(id);
    }

    @Override
    public void saveDocument(UserFile document) {
        dao.save(document);
    }

    @Override
    public List<UserFile> findAllByUserId(int userId) {
        return dao.findAllByUserId(userId);
    }

    @Override
    public void deleteById(int id) {
        dao.deleteById(id);
    }
}
