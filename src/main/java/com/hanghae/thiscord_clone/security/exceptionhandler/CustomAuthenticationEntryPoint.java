package com.hanghae.thiscord_clone.security.exceptionhandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanghae.thiscord_clone.Dto.response.ErrorResponseDto;
import com.hanghae.thiscord_clone.exception.custom.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Component
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private static final ErrorResponseDto errorResponseDto = new ErrorResponseDto(ErrorCode.UNAUTHORIZED_ERROR);

	@Override
	public void commence(HttpServletRequest request,
		HttpServletResponse response,
		AuthenticationException authenticationException) throws IOException {

		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(HttpStatus.UNAUTHORIZED.value());

		try (OutputStream os = response.getOutputStream()) {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.writeValue(os, errorResponseDto);
			os.flush();
		}
	}
}
