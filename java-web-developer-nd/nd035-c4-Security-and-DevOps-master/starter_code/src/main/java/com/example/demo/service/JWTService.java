package com.example.demo.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.example.demo.jwt.JWTBuilder;
import com.example.demo.jwt.JWTValidator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Slf4j
public class JWTService {
    @Value("${jwt_token_secret}")
    @Getter
    private String jwt_token_secret;

    @Value("${jwt_time_to_live_ms}")
    private @Getter
    long jwt_time_to_live_ms;

    @Getter
    @Value("${jwt_token_prefix}")
    private String jwt_token_prefix;

    public String buildToken(String username) throws Exception {
        try {
            JWTBuilder builder = new JWTBuilder(jwt_time_to_live_ms, jwt_token_secret);
            return builder.buildToken(username);
        } catch (Exception e) {
            e.printStackTrace();
            throw new JWTCreationException("JWTService: unable to create jwt token", null);
        }
    }

    public UsernamePasswordAuthenticationToken getAuthenticationToken(String token) {
        log.info("jwt token : {}", token);
        if (token != null) {
            JWTValidator validator = new JWTValidator(jwt_token_secret, jwt_token_prefix);
            return validator.getAuthenticationToken(token);
        }
        return null;
    }
}
