package com.example.demo.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public class JWTValidator {

    private String jwt_token_secret;

    private String  jwt_token_prefix;

    private String header;

    public JWTValidator(String jwt_token_secret, String jwt_token_prefix, String header) {
        this.header = header;
        this.jwt_token_prefix = jwt_token_prefix;
        this.jwt_token_secret = jwt_token_secret;
    }

    public UsernamePasswordAuthenticationToken getAuthenticationToken(@NotNull String token) {
        Algorithm algorithm = Algorithm.HMAC512(this.jwt_token_secret.getBytes());
        String user = JWT.require(algorithm)
                .build()
                .verify(token.replace(jwt_token_prefix, ""))
                .getSubject();

        if (user != null) {
            return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
        }

        return null;
    }
}
