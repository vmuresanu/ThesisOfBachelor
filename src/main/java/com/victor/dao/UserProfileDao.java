package com.victor.dao;

import com.victor.model.UserProfile;

import java.util.List;

public interface UserProfileDao {
    List<UserProfile> findAll();

    UserProfile findByType(String type);

    UserProfile findById(int id);
}
