package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.OrdersRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
@Transactional
public class OrderControllerTests {
    @Autowired
    private OrderController orderController;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemRepository itemRepository;

    private String password;

    private OrdersRequest ordersRequest;

    private String userName;

    private User user;

    @BeforeEach
    public void beforeEach() {
        password = "pegasus";
        userName = "test4";

        user = new User();
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setUsername(userName);

        Item item = itemRepository.findByName("Round Widget").get(0);

        Cart cart = new Cart();
        cart.addItem(item);
        cart.setUser(user);

        user.setCart(cart);
        userRepository.save(user);

        ordersRequest = new  OrdersRequest();
        ordersRequest.setUsername(user.getUsername());
    }

    @Test
    public void whenValidUserWitheItemsInCart_OrderIsSavedFromCart() {
        ResponseEntity<UserOrder> userOrderResponseEntity = orderController.submit(ordersRequest);
        Assertions.assertNotNull(userOrderResponseEntity);

        UserOrder userOrder = userOrderResponseEntity.getBody();
        Assertions.assertNotNull(userOrder);

        Cart cart = cartRepository.findByUser(user);
        List<Item> items = cart.getItems();
        Assertions.assertNotNull(cart);
        Assertions.assertNotNull(items);
        Assertions.assertNotNull(items);
        Assertions.assertEquals(items.size(), 1);
        Assertions.assertEquals(cart.getUser().getUsername(), userName);
    }
}
