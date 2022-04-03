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

    private DecodedJWT decoded;
    public JWTValidator(String jwt_token_secret, String jwt_token_prefix, @NotNull String token) {
        this.jwt_token_prefix = jwt_token_prefix;
        this.jwt_token_secret = jwt_token_secret;
        Algorithm algorithm = Algorithm.HMAC512(this.jwt_token_secret.getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        this.decoded = verifier.verify(token);
    }

    public boolean hasTokenNotExpired() {
        Date expiringDate = this.decoded.getExpiresAt();
        if(expiringDate.after(Date.from(Instant.ofEpochMilli((Instant.now().toEpochMilli()))))) {
            return true;
        }
        return false;
    }

    public boolean hasTokenSubject() {
        String user = this.decoded.getSubject();
        if(user != null) {
            return true;
        }
        return false;
    }

    public UsernamePasswordAuthenticationToken getAuthenticationToken() {
        String user = this.decoded.getSubject();
        return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
    }
}
