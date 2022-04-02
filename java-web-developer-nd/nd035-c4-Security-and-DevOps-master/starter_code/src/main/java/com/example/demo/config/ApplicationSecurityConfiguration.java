package com.example.demo.config;

import com.example.demo.filter.JWTUsernamePasswordAuthenticationFilter;
import com.example.demo.filter.TokenAuthorizationFilter;
import com.example.demo.service.JWTService;
import com.example.demo.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService;

    private final PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JWTService jwtService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http

                .addFilter(new JWTUsernamePasswordAuthenticationFilter(authenticationManagerBean(), jwtService));
        http
                .addFilterBefore(new TokenAuthorizationFilter(authenticationManagerBean(), jwtService), UsernamePasswordAuthenticationFilter.class);

       http = http
               .cors()
               .and()
               .csrf()
               .disable();
        http = http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();
        http
                .authorizeRequests()
                .antMatchers("/api/user/create").permitAll()
                .anyRequest().authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
