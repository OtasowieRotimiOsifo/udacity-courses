package com.example.demo.filter;

import com.example.demo.jwt.JWTValidator;
import com.example.demo.service.JWTService;
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

    private JWTService jwtService;

    public TokenAuthorizationFilter(AuthenticationManager authManager, JWTService jwtService) {
        super(authManager);
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(jwtService.getHeader());
        if(req.getServletPath().equals("/login")) {
            chain.doFilter(req, res);
            return ;
        } else  if (header == null || !header.startsWith( jwtService.getJwt_token_prefix())) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication =  jwtService.getAuthenticationToken(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }
}
