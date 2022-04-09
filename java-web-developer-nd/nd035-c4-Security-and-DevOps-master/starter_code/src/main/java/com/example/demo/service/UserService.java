package com.example.demo.service;

import com.example.demo.model.persistence.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

public interface UserService {
    public User saveUser(User user);
    public User findByUserName(String username);
    public Optional<User> findById(long id);
    public boolean matches(String raw, String encrypted);
}
