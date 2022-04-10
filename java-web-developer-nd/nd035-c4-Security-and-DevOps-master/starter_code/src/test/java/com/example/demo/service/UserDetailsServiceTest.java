package com.example.demo.service;

import com.example.demo.model.persistence.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

@SpringBootTest
public class UserDetailsServiceTest {
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private UserServiceImpl userService;

    private User user;
    private String password;

    @BeforeEach
    public void beforeEach() {
        password = "pegasus";
        user = new User();
        user.setPassword(password);
        user.setUsername("test7");

        User savedUser = userService.saveUser(user);
    }

    @Test
    public void whenLoadUserByUsername_thenReturnUserDetails() {

        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(user.getUsername());
        Assertions.assertNotNull(userDetails);
        Assertions.assertNotEquals(password, userDetails.getPassword());
        Assertions.assertTrue(userService.matches(password, userDetails.getPassword()));
    }
}
