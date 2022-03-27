package com.example.demo.service;

import com.example.demo.model.persistence.User;

import java.util.Optional;

public interface UserService {
    public User saveUser(User user);
    public User findByUserName(String username);
    public Optional<User> findById(long id);
}
