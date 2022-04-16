package com.example.demo.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.example.demo.jwt.JWTBuilder;
import com.example.demo.jwt.JWTValidator;
import com.sun.istack.NotNull;
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
    @Value("${jwt_token_secret:dGhlX2FyZ29uYXV0cw==}")
    @Getter
    private String jwt_token_secret;

    @Value("${jwt_time_to_live_ms:3600000}")
    private @Getter
    long jwt_time_to_live_ms;

    @Getter
    @Value("${jwt_token_prefix:Bearer}")
    private String jwt_token_prefix;

    public JWTService(String jwt_token_prefix, long jwt_time_to_live_ms, String jwt_token_secret) {
        this.jwt_token_prefix = jwt_token_prefix;
        this.jwt_time_to_live_ms = jwt_time_to_live_ms;
        this.jwt_token_secret = jwt_token_secret;
    }
    public String buildToken(String username) throws Exception {
        try {
            JWTBuilder builder = new JWTBuilder(jwt_time_to_live_ms, jwt_token_secret);
            return builder.buildToken(username);
        } catch (Exception e) {
            log.error("JWTService: unable to create jwt token for User with user name: {}", username);
            throw new JWTCreationException("JWTService: unable to create jwt token", null);
        }
    }

    public JWTValidator getJWTValidator(@NotNull String token) {
        return new JWTValidator(jwt_token_secret, jwt_token_prefix);
    }
}
