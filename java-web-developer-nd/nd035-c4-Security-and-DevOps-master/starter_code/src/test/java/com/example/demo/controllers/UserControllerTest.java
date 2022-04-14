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
import org.springframework.http.HttpStatus;
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

    @Test
    public void givenCreateUserPostRequest_UserWithValidPasswordIsCreatedAndStored() throws Exception {
        password = "pegAsu%s9";
        userName = "test1";

        createUserRequest = new CreateUserRequest();
        createUserRequest.setPassword(password);
        createUserRequest.setUsername(userName);

        ResponseEntity<Object> createdObject = userController.createUser(createUserRequest);
        Assertions.assertNotNull(createdObject);

        User createdUser = (User)createdObject.getBody();

        Cart cart = createdUser.getCart();
        Assertions.assertNotNull(cart);

        ResponseEntity<User> user =  userController.findByUserName(createUserRequest.getUsername());
        Assertions.assertNotNull(user.getBody());
    }

    @Test
    public void givenCreateUserPostRequest_UserIsNotCreatedDueToInValidPassword() throws Exception {
            password = "pegAsu%s";
            userName = "test1";

            createUserRequest = new CreateUserRequest();
            createUserRequest.setPassword(password);
            createUserRequest.setUsername(userName);

            ResponseEntity<Object> createdObject = userController.createUser(createUserRequest);
            Assertions.assertNotNull(createdObject);

            Assertions.assertEquals(createdObject.getStatusCode(), HttpStatus.NOT_ACCEPTABLE);

            String message = (String) createdObject.getBody();
        Assertions.assertEquals(message, "Password must contain at least 8 characters, digits,lower case, upper case and special characters");
    }

}
