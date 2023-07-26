package com.example.crm.service;

import com.example.crm.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(User user); //saveUser

    List<User> findAllUsers();

    Optional<User> findUserById(String id);

    User updateUser(User user, String id);

    void deleteUser(String id);

}

