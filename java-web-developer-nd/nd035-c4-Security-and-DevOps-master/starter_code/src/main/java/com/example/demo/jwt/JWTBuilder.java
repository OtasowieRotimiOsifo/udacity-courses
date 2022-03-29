package com.example.demo.jwt;

import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTCreationException;

import java.time.Instant;
import java.util.Date;

@Slf4j
public class JWTBuilder {

    @Value("${jwt_token_secret}")
    private String jwt_token_secret;

    @Value("${jwt_time_to_live_ms}")
    private long jwt_time_to_live_ms;

    public String buildToken(String username) throws Exception {
        try {
            Date validity = Date.from(Instant.ofEpochMilli((Instant.now().toEpochMilli()) + 600000));

            return JWT.create()
                    .withSubject(username)
                    .withExpiresAt(validity)
                    .sign(Algorithm.HMAC512("dGhlX2FyZ29uYXV0cw".getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new JWTCreationException("unable to create jwt token", null);
        }
    }
}
