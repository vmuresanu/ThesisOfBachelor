package com.victor.service;

import com.victor.model.UserDocument;

import java.util.List;

/**
 * Created by vmuresanu on 5/6/2017.
 */
public interface UserDocumentService {

    List<UserDocument> findAll();

    UserDocument findById(int id);

    void saveDocument(UserDocument document);

    List<UserDocument> findAllByUserId(int userId);

    void deleteById(int id);
}
