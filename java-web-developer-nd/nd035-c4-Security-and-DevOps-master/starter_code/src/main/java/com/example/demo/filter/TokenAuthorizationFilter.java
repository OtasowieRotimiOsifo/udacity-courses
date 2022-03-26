package com.example.demo.filter;

import com.example.demo.jwt.JWTValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenAuthorizationFilter extends BasicAuthenticationFilter {
    @Value("${header}")
    private String header_str;

    @Value("${jwt_token_prefix}")
    private String  jwt_token_prefix;

    private com.example.demo.jwt.JWTValidator validator = new JWTValidator();
    public TokenAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(header_str);

        if (header == null || !header.startsWith( jwt_token_prefix)) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication =  validator.getAuthenticationToken(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }
}
