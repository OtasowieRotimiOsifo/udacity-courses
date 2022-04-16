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
import java.util.List;

@DataJpaTest
public class UserOrderTests {
    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    private User user;

    private Cart cart;

    private User savedUser;

    @BeforeEach
    public void beforeEach() {
        String password = "pegasus";
        user = new User();
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setUsername("test5");

        Item item = new Item();
        item.setName("Round Widget");
        item.setDescription("A widget that is round");
        item.setPrice(BigDecimal.valueOf(2.99000));

        cart = new Cart();
        cart.addItem(item);
        cart.setUser(user);

        user.setCart(cart);
        savedUser = userRepository.save(user);
        cart.setUser(savedUser);
    }

    @Test
    public void whenValidUserWitheItemsInCart_OrderIsSavedFromCart() {

        UserOrder order = UserOrder.createFromCart(cart);

        UserOrder savedOrder = orderRepository.save(order);
        Assertions.assertNotNull(savedOrder);
        Assertions.assertNotNull(savedOrder.getItems());
        Assertions.assertNotNull(savedOrder.getTotal());
        Assertions.assertEquals(savedOrder.getItems().size(), 1);
        Assertions.assertEquals(savedOrder.getUser().getUsername(), "test5");

        List<UserOrder> userOrders = orderRepository.findByUser(user);
        Assertions.assertNotNull(userOrders);
        Assertions.assertEquals(userOrders.size(), 1);
        for(UserOrder o: userOrders) {
            Assertions.assertNotNull(o.getItems());
            Assertions.assertNotNull(o.getTotal());
            Assertions.assertEquals(o.getItems().size(), 1);
            Assertions.assertEquals(o.getUser().getUsername(), "test5");
        }
    }
}
