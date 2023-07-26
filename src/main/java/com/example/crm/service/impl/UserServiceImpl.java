package com.example.crm.service.impl;

import com.example.crm.entity.User;
import com.example.crm.repository.UserRepository;
import com.example.crm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(User user) {
        User userCreated = new User(
                user.getFirstName(),
                user.getLastName(),
                user.getFullName(),
                user.getUsername(),
                user.getPhoneNumber(),
                user.getPassword(),
                user.getBirthday(),
                user.getGender()
        );
        userRepository.save(userCreated);
        return userCreated;
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findUserById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public User updateUser(User user, String id) {
        User existingUser = userRepository.findById(id).get();
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setPhoneNumber(user.getPhoneNumber());
        existingUser.setBirthday(user.getBirthday());
        existingUser.setGender(user.getGender());
        User userSaved = userRepository.save(existingUser);
        return userSaved;
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}

