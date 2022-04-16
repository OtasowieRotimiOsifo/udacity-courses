package com.example.demo.model;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;

import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;

@DataJpaTest
public class UserCartTests {

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void beforeEach() {
        String password = "pegasus";
        user = new User();
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setUsername("test3");

        Item item = new Item();
        item.setName("Round Widget");
        item.setDescription("A widget that is round");
        item.setPrice(BigDecimal.valueOf(2.99000));

        Cart cart = new Cart();
        cart.addItem(item);
        cart.setUser(user);
        cartRepository.save(cart);

        user.setCart(cart);
        userRepository.save(user);
    }

    @Test
    public void whenValidUser_SavedCartIsReturned() {
        Cart cart = cartRepository.findByUser(user);
        Assertions.assertNotNull(cart);
        Assertions.assertNotNull(cart.getItems());
        Assertions.assertNotNull(cart.getTotal());
        Assertions.assertEquals(cart.getItems().size(), 1);
        Assertions.assertEquals(cart.getUser().getUsername(), "test3");
    }
}
