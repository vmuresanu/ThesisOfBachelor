package com.victor.dao;

import com.victor.model.UserFile;

import java.util.List;

public interface UserFileDao {
    List<UserFile> findAll();

    UserFile findById(int id);

    void save(UserFile document);

    List<UserFile> findAllByUserId(int userId);

    void deleteById(int id);

}
