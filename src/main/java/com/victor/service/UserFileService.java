package com.victor.service;

import com.victor.model.UserFile;

import java.util.List;

public interface UserFileService {

    List<UserFile> findAll();

    UserFile findById(int id);

    void saveDocument(UserFile document);

    List<UserFile> findAllByUserId(int userId);

    void deleteById(int id);
}
