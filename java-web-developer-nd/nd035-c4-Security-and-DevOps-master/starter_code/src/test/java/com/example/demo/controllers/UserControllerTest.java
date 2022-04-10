package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

@SpringBootTest
public class UserControllerTest {
    @Autowired
    private UserController userController;

    @Autowired
    private UserRepository userRepository;

    private String password;

    private CreateUserRequest createUserRequest;

    private String userName;

    @BeforeEach
    public void beforeEach() {
        password = "pegasus";
        userName = "test1";
        createUserRequest = new CreateUserRequest();
        createUserRequest.setPassword(password);
        createUserRequest.setUsername(userName);
    }

    @Test
    public void givenCreateUserPostRequest_UserIsCreatedAndStored() throws Exception {
        ResponseEntity<User> createdUser = userController.createUser(createUserRequest);
        Assertions.assertNotNull(createdUser);

        Cart cart = createdUser.getBody().getCart();
        Assertions.assertNotNull(cart);

        ResponseEntity<User> user =  userController.findByUserName(createUserRequest.getUsername());
        Assertions.assertNotNull(user.getBody());
    }

    @AfterEach
    public void afterEach() {
        User user = userRepository.findByUsername( createUserRequest.getUsername());
        userRepository.delete(user);
    }
}
