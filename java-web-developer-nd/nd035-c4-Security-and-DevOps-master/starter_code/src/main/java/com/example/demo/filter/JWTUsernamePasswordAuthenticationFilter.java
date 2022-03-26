package com.example.demo.filter;

import com.example.demo.jwt.JWTBuilder;
import com.example.demo.model.persistence.User;
import liquibase.pro.packaged.as;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JWTUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private static Logger logger = LoggerFactory.getLogger(JWTUsernamePasswordAuthenticationFilter.class);

    public JWTUsernamePasswordAuthenticationFilter() {
        setFilterProcessesUrl("/login");
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response)
            throws AuthenticationException {

        try {
            UsernamePasswordAuthenticationToken authRequest = getAuthRequest(request);
            setDetails(request, authRequest);

            return this.getAuthenticationManager().authenticate(authRequest);
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

        String username = ((User) auth.getPrincipal()).getUsername();

        try {
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

        String username = obtainUsername(request);
        String password = obtainPassword(request);

        logger.info("username: {}, password: {}", username, password);
        return new UsernamePasswordAuthenticationToken(
                username, password, new ArrayList<>());
    }
}
