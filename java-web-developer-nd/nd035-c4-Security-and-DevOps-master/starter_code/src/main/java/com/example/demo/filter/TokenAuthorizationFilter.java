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
import java.util.Map;
import java.util.Set;

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
                Map<String, String[]> parameters = req.getParameterMap();
                for (String k : parameters.keySet()) {
                    String[] p = parameters.get(k);
                    for (int i = 0; i < p.length; i++) {
                        log.info("key: {}, parameter: {}", k, p[i]);
                    }
                }
                String token = header.substring(jwtService.getJwt_token_prefix().length());
                log.info("JWT token in controller: {}", token);
                if (token != null) {
                    String trimmed = token.trim();
                    JWTValidator validator = jwtService.getJWTValidator(trimmed);

                    validator.verifyToken(trimmed);

                    if (!validator.verifyHasSubject(trimmed)) {
                        Map<String, String> error = ErrorUtils.getErrorMap(res, "The JWT has no subject!");
                        ObjectMapper mapper = new ObjectMapper();
                        mapper.writeValue(res.getOutputStream(), error);
                        return;
                    }

                    UsernamePasswordAuthenticationToken authentication = validator.getAuthenticationToken(trimmed);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    chain.doFilter(req, res);
                    return;
                }

                Map<String, String> error = ErrorUtils.getErrorMap(res, "JWT token is null!");
                ObjectMapper mapper = new ObjectMapper();
                mapper.writeValue(res.getOutputStream(), error);

                return;
            } else {
                Map<String, String> error = ErrorUtils.getErrorMap(res, "Not authorized!");
                ObjectMapper mapper = new ObjectMapper();
                mapper.writeValue(res.getOutputStream(), error);
                return;
            }

        } catch (Exception e) {
            Map<String, String> error = ErrorUtils.getErrorMap(res, e.getMessage());
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(res.getOutputStream(), error);
            log.info("JWT authorization failed for User with user name: {}", req.getParameter("username"));
        }

    }
}
