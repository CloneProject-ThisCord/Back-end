package com.hanghae.thiscord_clone.security.exceptionhandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanghae.discord_clone.exception.custom.CustomSecurityException;
import com.hanghae.discord_clone.exception.custom.ErrorCode;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private final ObjectMapper om;

	private static final String DEFAULT_ERROR_MSG = "Full authentication is required to access this resource";
	private static final String CUSTOM_DEFAULT_ERROR_MSG = "문제가 발생했습니다.";


	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
		throws IOException, JsonProcessingException {
		log.error(DEFAULT_ERROR_MSG);

		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");

		String errorMsg = authException.getMessage();

		if(errorMsg.equals(DEFAULT_ERROR_MSG)){
			errorMsg = CUSTOM_DEFAULT_ERROR_MSG;
		}

		CustomSecurityException errorResponse = new CustomSecurityException(
			ErrorCode.UNAUTHORIZED_ERROR);

		String result = om.writeValueAsString(errorResponse);

		response.getWriter().write(result);

		response.setStatus(HttpStatus.UNAUTHORIZED.value());
	}
}
