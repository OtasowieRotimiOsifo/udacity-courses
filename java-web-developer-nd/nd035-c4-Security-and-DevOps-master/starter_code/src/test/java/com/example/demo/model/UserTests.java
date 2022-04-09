package com.example.demo.model;

import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@DataJpaTest
public class UserTests {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Test
    public void whenFindByUsername_thenReturnUser() {

        String password= "pegasus";
        User user = new User();
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setUsername("test1");


        entityManager.persist(user);
        entityManager.flush();

        User found = userRepository.findByUsername(user.getUsername());

        Assertions.assertNotNull(found);
        Assertions.assertEquals(found.getUsername(), user.getUsername());
        Assertions.assertTrue(bCryptPasswordEncoder.matches(password, found.getPassword()));
    }
}
