package com.example.demo.JWT;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.jwt.JWTBuilder;
import org.junit.Assert;
import org.junit.Test;

public class JWTBuilderTest {
    @Test
    public void validateJWT() throws Exception {
        String jwt_token_secret = "dGhlX2FyZ29uYXV0cw==";
        long jwt_time_to_live_ms = 3600000;
        String userName = "test1";
        JWTVerifier verifier;

        JWTBuilder builder = new JWTBuilder(jwt_time_to_live_ms, jwt_token_secret);

        String token = builder.buildToken(userName);

        DecodedJWT decoded = JWT.decode(token);
        String actual =  Algorithm.HMAC512(jwt_token_secret.getBytes()).getName();
        String expected = decoded.getAlgorithm();

        Assert.assertEquals(actual.hashCode(), expected.hashCode());
    }
}
