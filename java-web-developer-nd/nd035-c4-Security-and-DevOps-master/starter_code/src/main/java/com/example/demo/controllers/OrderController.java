package com.example.demo.controllers;

import java.util.List;

import com.example.demo.model.requests.OrdersRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/api/order")
@Transactional
@Slf4j
public class OrderController {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrderRepository orderRepository;

	@PostMapping("/submit")
	public ResponseEntity<UserOrder> submit(@RequestBody OrdersRequest request) {
		User user = userRepository.findByUsername(request.getUsername());
		if(user == null) {
			log.error("User with user name: {} not found in the system for submit order", request.getUsername());
			return ResponseEntity.notFound().build();
		}
		UserOrder order = UserOrder.createFromCart(user.getCart());
		orderRepository.save(order);
		log.info("Order submitted and saved for User with user name: {}", request.getUsername());
		return ResponseEntity.ok(order);
	}

	@GetMapping("/history")
	public ResponseEntity<List<UserOrder>> getOrdersForUser(@RequestBody OrdersRequest request) {
		User user = userRepository.findByUsername(request.getUsername());
		if(user == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(orderRepository.findByUser(user));
	}
}
