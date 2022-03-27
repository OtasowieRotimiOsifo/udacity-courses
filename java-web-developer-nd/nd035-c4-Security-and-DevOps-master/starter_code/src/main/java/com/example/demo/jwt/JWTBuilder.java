package com.example.demo.jwt;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTCreationException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

public class JWTBuilder {

    @Value("${jwt_token_secret}")
    private String jwt_token_secret;

    @Value("${jwt_time_to_live_ms}")
    private long jwt_time_to_live_ms;

    public String buildToken(String username) throws Exception {
        try {
            Date validity = Date.from(Instant.ofEpochMilli((Instant.now().toEpochMilli()) + jwt_time_to_live_ms));
            return JWT.create()
                    .withSubject(username)
                    .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                    .sign(Algorithm.HMAC256(jwt_token_secret.getBytes()));
        } catch (Exception e) {
            throw new JWTCreationException("unable to create jwt token", null);
        }
    }
}
