package com.victor.service;

import com.victor.model.User;

import java.util.List;

public interface UserService {

    User findById(int id);

    User findBySSO(String ssoId);

    List<User> findAllUsers();

    boolean isUserSSOUnique(Integer id, String ssoId);

    void saveUser(User user);

    void updateUser(User user);

    void deleteUserBySSO(String ssoId);
}
