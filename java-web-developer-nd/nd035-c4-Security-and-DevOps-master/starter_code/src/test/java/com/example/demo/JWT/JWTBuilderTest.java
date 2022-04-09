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
    public void validateCreateJWT() throws Exception {
        String jwt_token_secret = "dGhlX2FyZ29uYXV0cw==";
        long jwt_time_to_live_ms = 3600000;
        String userName = "test1";

        JWTBuilder builder = new JWTBuilder(jwt_time_to_live_ms, jwt_token_secret);

        String token = builder.buildToken(userName);

        Assert.assertNotNull(token);

        Algorithm algorithm =  Algorithm.HMAC512(jwt_token_secret.getBytes());
        JWTVerifier verifier =  JWT.require(algorithm).build();
        DecodedJWT decoded = verifier.verify(token);

        Assert.assertNotNull(decoded);
    }

    @Test
    public void validateJWTAlgorithm() throws Exception {
        String jwt_token_secret = "dGhlX2FyZ29uYXV0cw==";
        long jwt_time_to_live_ms = 3600000;
        String userName = "test1";
        JWTVerifier verifier;

        JWTBuilder builder = new JWTBuilder(jwt_time_to_live_ms, jwt_token_secret);

        String token = builder.buildToken(userName);

        Assert.assertNotNull(token);

        DecodedJWT decoded = JWT.decode(token);

        String algorithm =  Algorithm.HMAC512(jwt_token_secret.getBytes()).getName();
        String expected = decoded.getAlgorithm();

        Assert.assertEquals(algorithm.hashCode(), expected.hashCode());
    }

    @Test
    public void validateJWTSubject() throws Exception {
        String jwt_token_secret = "dGhlX2FyZ29uYXV0cw==";
        long jwt_time_to_live_ms = 3600000;
        String userName = "test1";

        JWTBuilder builder = new JWTBuilder(jwt_time_to_live_ms, jwt_token_secret);

        String token = builder.buildToken(userName);

        Assert.assertNotNull(token);

        DecodedJWT decoded = JWT.decode(token);
        String subject = decoded.getSubject();

        Assert.assertEquals(userName, subject);
    }
}
