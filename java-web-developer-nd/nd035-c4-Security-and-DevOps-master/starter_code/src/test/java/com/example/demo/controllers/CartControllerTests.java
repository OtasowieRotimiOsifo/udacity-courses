package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

@SpringBootTest
public class CartControllerTests {
    @Autowired
    private CartController cartController;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    private String password;

    private ModifyCartRequest cartRequest;

    private String userName;

    private User user;
    private User createdUser;

    @BeforeEach
    public void beforeEach() {
        password = "pegasus";
        userName = "test20";

        user = new User();
        user.setPassword(password);
        user.setUsername(userName);

        Cart cart = new Cart();
        user.setCart(cart);
        cart.setUser(user);
        createdUser = userRepository.save(user);
        cartRepository.save(cart);

        Item item = itemRepository.findByName("Round Widget").get(0);

        cartRequest = new ModifyCartRequest();
        cartRequest.setUsername(createdUser.getUsername());
        cartRequest.setItemId(item.getId());
        cartRequest.setQuantity(1);
    }

    @Test
    public void givenAddToCartPostRequest_UserIsModifiedAndStored() throws Exception {
        ResponseEntity<Cart> createdCart = cartController.addTocart(cartRequest);
        Assertions.assertNotNull(createdCart);

        Cart cart = createdCart.getBody();
        Assertions.assertNotNull(cart);

        Cart cartOwnedByUser =  cartRepository.findByUser(user);
        Assertions.assertNotNull(cartOwnedByUser);
    }
}
