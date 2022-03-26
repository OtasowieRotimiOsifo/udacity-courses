package com.example.demo.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public class JWTValidator {

    @Value("${jwt_token_secret}")
    private String jwt_token_secret;

    @Value("${jwt_token_prefix}")
    private String  jwt_token_prefix;

    @Value("${header}")
    private String header;

    public UsernamePasswordAuthenticationToken getAuthenticationToken(HttpServletRequest request) {
        String token = request.getHeader(header);

        if (token != null) {
            // parse the token.
            String user = JWT.require(Algorithm.HMAC512(jwt_token_secret))
                    .build()
                    .verify(token.replace(jwt_token_prefix, ""))
                    .getSubject();

            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            }

            return null;
        }
        return null;
    }
}
