package com.example.demo.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sun.istack.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

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
        this.decoded = verifier.verify(token); // throws JWTVerificationException if verification fails.
    }

    public boolean verifyHasSubject(@NotNull String token) {
        String user = this.decoded.getSubject();
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
