package com.example.demo.service;

import com.example.demo.model.persistence.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userService.findByUserName(username);
            if(user == null) {
                log.error("User with user name: {} not found in the database");
                throw new UsernameNotFoundException("User not found in the database");
            } else {
                log.info("User with user name: {} found in the database", username);
                return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
            }
        } catch(UsernameNotFoundException e) {
            log.error("Error in loadUserByUsername with message: {}", e.getMessage());
            throw new UsernameNotFoundException(e.getMessage());
        }

    }
}
