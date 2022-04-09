package com.example.demo.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class JWTBuilderTests {
    private String jwt_token_secret = "dGhlX2FyZ29uYXV0cw==";
    private long jwt_time_to_live_ms =  60000;
    private String userName = "test1";

    private JWTBuilder builder = new JWTBuilder(jwt_time_to_live_ms, jwt_token_secret);


    @Test
    public void validateCreateJWT() throws Exception {

        String token = builder.buildToken(userName);

        Assertions.assertNotNull(token);

        Algorithm algorithm =  Algorithm.HMAC512(jwt_token_secret.getBytes());
        JWTVerifier verifier =  JWT.require(algorithm).build();
        DecodedJWT decoded = verifier.verify(token);

        Assertions.assertNotNull(decoded);
    }

    @Test
    public void validateJWTAlgorithm() throws Exception {

        String token = builder.buildToken(userName);

        Assertions.assertNotNull(token);

        DecodedJWT decoded = JWT.decode(token);

        String algorithm =  Algorithm.HMAC512(jwt_token_secret.getBytes()).getName();
        String expected = decoded.getAlgorithm();

        Assertions.assertEquals(algorithm.hashCode(), expected.hashCode());
    }

    @Test
    public void validateJWTSubject() throws Exception {

        String token = builder.buildToken(userName);

        Assertions.assertNotNull(token);

        DecodedJWT decoded = JWT.decode(token);
        String subject = decoded.getSubject();

        Assertions.assertEquals(userName, subject);
    }
}
