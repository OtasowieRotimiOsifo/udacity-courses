package com.example.demo.model;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;

@DataJpaTest
public class UserOrderTests {
    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    private User user;

    @BeforeEach
    public void beforeEach() {
        String password = "pegasus";
        user = new User();
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setUsername("test1");

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
    public void whenValidUserWitheItemsInCart_OrderIsSavedFromCart() {
        Cart cart = cartRepository.findByUser(user);

        UserOrder order = UserOrder.createFromCart(cart);

        UserOrder savedOrder = orderRepository.save(order);
        Assertions.assertNotNull(savedOrder);
        Assertions.assertNotNull(savedOrder.getItems());
        Assertions.assertNotNull(savedOrder.getTotal());
        Assertions.assertEquals(savedOrder.getItems().size(), 1);
        Assertions.assertEquals(savedOrder.getUser().getUsername(), "test1");
    }
}
