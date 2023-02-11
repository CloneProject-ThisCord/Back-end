package com.hanghae.thiscord_clone.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanghae.thiscord_clone.security.JwtConfig;
import com.hanghae.thiscord_clone.security.exceptionhandler.CustomAccessDeniedHandler;
import com.hanghae.thiscord_clone.security.exceptionhandler.CustomAuthenticationEntryPoint;
import com.hanghae.thiscord_clone.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig {

	private final JwtUtil jwtUtil;
	private final ObjectMapper om;
	private final CorsConfig corsConfig;
	private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
	private final CustomAccessDeniedHandler customAccessDeniedHandler;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
		http
			.csrf().disable() // 기본값이 on인 csrf 취약점 보안을 해제한다. on으로 설정해도 되나 설정할경우 웹페이지에서 추가처리가 필요함.
//			.headers()
//			.frameOptions().sameOrigin() // SockJS는 기본적으로 HTML iframe 요소를 통한 전송을 허용하지 않도록 설정되는데 해당 내용을 해제한다.

			.addFilter(corsConfig.corsFilter())
			.authorizeRequests()
			.antMatchers("/api/user/**").permitAll()
			.antMatchers("/ws/chat/**").permitAll()
			.antMatchers("/ws/**").permitAll()
			.anyRequest().authenticated()

			.and()
			.apply(new JwtConfig(jwtUtil, om)); // 나머지 리소스에 대한 접근 설정

		http.exceptionHandling()
			.authenticationEntryPoint(customAuthenticationEntryPoint);

		http.exceptionHandling()
			.accessDeniedHandler(customAccessDeniedHandler);

		return http.build();
	}

}