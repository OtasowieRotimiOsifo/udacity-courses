package com.example.demo.service;

import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.UserRepository;

import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {



    private PasswordEncoder bCryptPasswordEncoder =  new BCryptPasswordEncoder();
    //@InjectMocks
    private UserServiceImpl userService;

    @Mock
    private  UserRepository userRepository;

    @Test
    public void userIsSuccessfullyCreatedAndSavedWithEncryptedPassword() {
        User user = new User();
        user.setPassword("pegasus");
        user.setUsername("test1");

        Mockito.when(userRepository.save(user)).thenReturn(user);

        userService = new UserServiceImpl(bCryptPasswordEncoder, userRepository);
        User savedUser = userService.saveUser(user);

        Assertions.assertNotNull(savedUser);
        Assertions.assertNotEquals("pegasus", savedUser.getPassword());
    }

    @Test
    public void whenValidName_thenUserShouldBeFound() {
        User user = new User();
        user.setPassword("pegasus");
        user.setUsername("test1");

        Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(user);
        Mockito.when(userRepository.save(user)).thenReturn(user);

        userService = new UserServiceImpl(bCryptPasswordEncoder, userRepository);
        User savedUser = userService.saveUser(user);
        User foundUser = userService.findByUserName(savedUser.getUsername());

        Assertions.assertNotNull(savedUser);
        Assertions.assertNotEquals("pegasus", foundUser.getPassword());
    }
}
