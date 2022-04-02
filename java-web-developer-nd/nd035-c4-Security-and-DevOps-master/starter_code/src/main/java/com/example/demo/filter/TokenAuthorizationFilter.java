package com.example.demo.filter;

import com.example.demo.jwt.JWTValidator;
import com.example.demo.service.JWTService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

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

        try {
            if (req.getServletPath().equals("/login")) {
                chain.doFilter(req, res);
                return;
            } /*else  if (header == null || !header.startsWith( jwtService.getJwt_token_prefix())) {
            chain.doFilter(req, res);
            return;
        }*/

            String header = req.getHeader(org.springframework.http.HttpHeaders.AUTHORIZATION);
            String token = header.substring(jwtService.getJwt_token_prefix().length());
            UsernamePasswordAuthenticationToken authentication = jwtService.getAuthenticationToken(token);

            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(req, res);
        } catch (Exception exception) {

            res.setHeader("error", exception.getMessage());
            res.setStatus(FORBIDDEN.value());

            Map<String, String> error = new HashMap<>();
            error.put("error_message", exception.getMessage());

            res.setContentType(APPLICATION_JSON_VALUE);

            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(res.getOutputStream(), error);
        }

    }
}
