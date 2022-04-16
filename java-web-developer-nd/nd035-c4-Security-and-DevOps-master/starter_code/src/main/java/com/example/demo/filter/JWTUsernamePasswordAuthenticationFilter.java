package com.example.demo.filter;

import com.example.demo.jwt.JWTBuilder;

import com.example.demo.service.JWTService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class JWTUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private JWTService jwtService;

    public JWTUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager, JWTService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response)
            throws AuthenticationException {

        try {
            UsernamePasswordAuthenticationToken authRequest = getAuthRequest(request);
            setDetails(request, authRequest);
            log.info("Set details made for User with user name: {}", request.getParameter("username"));
            return this.authenticationManager.authenticate(authRequest);
        } catch (Exception e) {
            log.info("error message: {}", e.getMessage());
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException {

        org.springframework.security.core.userdetails.User u = (org.springframework.security.core.userdetails.User) auth.getPrincipal();
        String username = u.getUsername();
        log.info("user name in successfulAuthentication: {}", username);

        try {
            String token = jwtService.buildToken(username);

            Map<String, String> tokens = new HashMap<>();
            tokens.put("access_token", token);

            res.setContentType(APPLICATION_JSON_VALUE);

            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(res.getOutputStream(), tokens);

        } catch (Exception e) {
            log.error("Authentication failed for User with user name: {}", username);
        }
    }

    private UsernamePasswordAuthenticationToken getAuthRequest(
            HttpServletRequest request) {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        log.info("Authentication token will be created for User with user name: {}", username);
        return new UsernamePasswordAuthenticationToken(
                username, password, new ArrayList<>());
    }
}
