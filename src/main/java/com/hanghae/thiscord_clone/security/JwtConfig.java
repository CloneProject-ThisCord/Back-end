package com.hanghae.thiscord_clone.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanghae.discord_clone.security.jwt.JwtAuthFilter;
import com.hanghae.discord_clone.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class JwtConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final JwtUtil jwtUtil;
    private final ObjectMapper om;

    @Override
    public void configure(HttpSecurity http) {
        JwtAuthFilter jwtAuthFilter = new JwtAuthFilter(jwtUtil, om);
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
