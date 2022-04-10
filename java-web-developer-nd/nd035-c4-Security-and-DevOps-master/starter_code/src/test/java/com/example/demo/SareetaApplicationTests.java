package com.example.demo;

import com.example.demo.controllers.CartController;

import com.example.demo.controllers.ItemController;
import com.example.demo.controllers.OrderController;
import com.example.demo.controllers.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@SpringBootTest
public class SareetaApplicationTests {

	@Autowired
	private CartController cartController;

	@Autowired
	private ItemController itemController;

	@Autowired
	private OrderController orderController;

	@Autowired
	private UserController userController;

	@Test
	public void contextLoads() throws Exception {
		Assertions.assertNotNull(cartController);
		Assertions.assertNotNull(itemController);
		Assertions.assertNotNull(orderController);
		Assertions.assertNotNull(userController);
	}

}
