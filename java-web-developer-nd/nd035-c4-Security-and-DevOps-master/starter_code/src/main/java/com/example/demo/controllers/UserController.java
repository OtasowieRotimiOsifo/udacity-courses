package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/api/user")
@Transactional
public class UserController {
	private static Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private CartRepository cartRepository;

	@GetMapping("/id/{id}")
	public ResponseEntity<User> findById(@PathVariable Long id) {
		return ResponseEntity.of(userService.findById(id));
	}
	
	@GetMapping("/{username}")
	public ResponseEntity<User> findByUserName(@PathVariable String username) {
		User user = userService.findByUserName(username);
		return user == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
	}
	
	@PostMapping("/create")
	public ResponseEntity<Object> createUser(@RequestBody CreateUserRequest createUserRequest)  {
		if(passWordIsValid(createUserRequest.getPassword())) {
			logger.info("user name: {}", createUserRequest.getUsername());
			User user = new User();
			user.setUsername(createUserRequest.getUsername());
			user.setPassword(createUserRequest.getPassword()); //must be encrypted: a class should do this.

			Cart cart = new Cart();

			user.setCart(cart);
			User savedUser = userService.saveUser(user);
			cart.setUser(user);
			cartRepository.save(cart);
			return ResponseEntity.ok(savedUser);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Password must contain at least 8 characters, digits,lower case, upper case and special characters");
		}

	}

	private boolean passWordIsValid(String password){

		if (password == null) {
			return false;
		}

		String regex = "^(?=.*[0-9])"
				+ "(?=.*[a-z])(?=.*[A-Z])"
				+ "(?=.*[@#$%^&+=])"
				+ "(?=\\S+$).{8,15}$";

		Pattern p = Pattern.compile(regex);

		Matcher m = p.matcher(password);

		return m.matches();
	}
}
