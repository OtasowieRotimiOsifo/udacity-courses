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
    private static Logger logger = LoggerFactory.getLogger(JWTUsernamePasswordAuthenticationFilter.class);

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
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            logger.info("username 1: {}, password 1: {}", username, password);
            logger.info("path 1: {}", request.getPathInfo());
            UsernamePasswordAuthenticationToken authRequest = getAuthRequest(request);
            setDetails(request, authRequest);

            return this.authenticationManager.authenticate(authRequest);
        } catch (Exception e) {
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
        logger.info("username 2: {}", username);

        try {
            String token = jwtService.buildToken(username);

            Map<String, String> tokens = new HashMap<>();
            tokens.put("access_token", token);

            res.setContentType(APPLICATION_JSON_VALUE);

            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(res.getOutputStream(), tokens);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private UsernamePasswordAuthenticationToken getAuthRequest(
            HttpServletRequest request) {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        logger.info("username: {}, password: {}", username, password);
        logger.info("path: {}", request.getPathInfo());
        return new UsernamePasswordAuthenticationToken(
                username, password, new ArrayList<>());
    }
}
