package com.example.demo.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sun.istack.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.ArrayList;

@Slf4j
public class JWTValidator {

    private String jwt_token_secret;

    private String  jwt_token_prefix;

    private JWTVerifier verifier;

    private DecodedJWT decoded;

    public JWTValidator(String jwt_token_secret, String jwt_token_prefix) {
        this.jwt_token_prefix = jwt_token_prefix;
        this.jwt_token_secret = jwt_token_secret;
        Algorithm algorithm = Algorithm.HMAC512(this.jwt_token_secret.getBytes());
        this.verifier = JWT.require(algorithm).build();
        this.decoded = null;

    }


    public void verifyToken(@NotNull String token) {
        try {
            this.decoded = verifier.verify(token); // throws JWTVerificationException if verification fails.
            String user = this.decoded.getSubject();
            log.info("user in validator verify: {}", user);
        } catch (JWTVerificationException e) {
            log.error("verification of JWT failed with message: {}", e.getMessage());
        }

    }

    public boolean verifyHasSubject(@NotNull String token) {

        String user = this.decoded.getSubject();
        log.info("user in validator: {}", user);
        if(user != null) {
            return true;
        }
        return false;
    }

    public UsernamePasswordAuthenticationToken getAuthenticationToken(@NotNull String token) {
        String user = this.decoded.getSubject();
        return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
    }
}
