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

    private String jwt_token_secret;

    private long jwt_time_to_live_ms;

    public JWTBuilder(long jwt_time_to_live_ms, String jwt_token_secret) {
        this.jwt_time_to_live_ms = jwt_time_to_live_ms;
        this.jwt_token_secret = jwt_token_secret;
    }
    public String buildToken(String username) throws Exception {
        try {
            Algorithm algorithm = Algorithm.HMAC512(this.jwt_token_secret.getBytes());
            Date validity = Date.from(Instant.ofEpochMilli((Instant.now().toEpochMilli()) + this.jwt_time_to_live_ms));

            return JWT.create()
                    .withSubject(username)
                    .withExpiresAt(validity)
                    .sign(algorithm);
        } catch (Exception e) {
            log.error("Unable to create jwt token for User with user name: {}", username);
            throw new JWTCreationException("Unable to create jwt token for user", null);
        }
    }
}
