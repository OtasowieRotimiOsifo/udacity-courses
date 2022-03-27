package com.example.demo.filter;

import com.example.demo.jwt.JWTBuilder;
import com.example.demo.model.persistence.User;
import liquibase.pro.packaged.as;
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

@Slf4j
public class JWTUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private static Logger logger = LoggerFactory.getLogger(JWTUsernamePasswordAuthenticationFilter.class);

    private final AuthenticationManager authenticationManager;

    public JWTUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;

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


        JWTBuilder builder = new JWTBuilder();

        org.springframework.security.core.userdetails.User u = (org.springframework.security.core.userdetails.User) auth.getPrincipal();
        String username = u.getUsername();

        try {
            logger.info("username 2: {}", username);
            String token = builder.buildToken(username);
            String body = username  + " " + token;

            res.getWriter().write(body);
            res.getWriter().flush();

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
