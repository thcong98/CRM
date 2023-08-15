package com.example.crm.service;

import com.example.crm.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    List<User> findAllUsers();

    Optional<User> findUserById(String id);

    User updateUser(User user, String id);

    void deleteUser(UUID id);
}

