package com.victor.service;

import com.victor.model.UserProfile;

import java.util.List;

public interface UserProfileService {
    UserProfile findById(int id);

    UserProfile findByType(String type);

    List<UserProfile> findAll();
}
