package com.example.demo.filter;

import com.example.demo.jwt.JWTValidator;
import com.example.demo.service.JWTService;
import com.example.demo.utils.ErrorUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
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

        if (req.getServletPath().equals("/login") || req.getServletPath().equals("/api/user/create")) {
            chain.doFilter(req, res);
            return;
        }

        try {

            String header = req.getHeader(org.springframework.http.HttpHeaders.AUTHORIZATION);

            if(header != null && header.startsWith(jwtService.getJwt_token_prefix())) {

                log.info("Authorization header: {}", header);
                String token = header.substring(jwtService.getJwt_token_prefix().length());
                if(token != null) {
                    String trimmed = token.trim();
                    JWTValidator validator = jwtService.getJWTValidator(trimmed);

                    if(!validator.hasTokenNotExpired()) {
                        Map<String, String>  error = ErrorUtils.getErrorMap(res, "The JWT token has expired!");
                        ObjectMapper mapper = new ObjectMapper();
                        mapper.writeValue(res.getOutputStream(), error);
                        return;
                    }
                    if(!validator.hasTokenSubject()) {
                        Map<String, String>  error = ErrorUtils.getErrorMap(res, "The JWT has no subject!");
                        ObjectMapper mapper = new ObjectMapper();
                        mapper.writeValue(res.getOutputStream(), error);
                        return;
                    }

                    UsernamePasswordAuthenticationToken authentication = validator.getAuthenticationToken();
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    chain.doFilter(req, res);
                    return;
                }
            } else {
                Map<String, String>  error = ErrorUtils.getErrorMap(res, "Not authorized!");
                ObjectMapper mapper = new ObjectMapper();
                mapper.writeValue(res.getOutputStream(), error);
                return;
            }

        } catch (Exception e) {
            Map<String, String>  error = ErrorUtils.getErrorMap(res, e.getMessage());
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(res.getOutputStream(), error);
        }

    }
}