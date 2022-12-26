package com.hanghae.thiscord_clone.exception;

import com.hanghae.discord_clone.exception.custom.CommonException;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class RestApiExceptionHandler {

	//Custom Exception
	@ExceptionHandler(value = {CommonException.class})
	@ResponseStatus(code = HttpStatus.OK)
	public ResponseEntity<Object> handleCustomException(CommonException exception) {
		RestApiException restApiException = new RestApiException();
		restApiException.setMsg(exception.getMsg());
		restApiException.setStatusCode(HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<>(restApiException, HttpStatus.BAD_REQUEST);
	}


	//Valid Exception
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(code = HttpStatus.OK)
	public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException exception) {
		RestApiException restApiException = new RestApiException();
		restApiException.setMsg(Objects.requireNonNull(exception.getFieldError()).getDefaultMessage());
		restApiException.setStatusCode(HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<>(restApiException, HttpStatus.BAD_REQUEST);
	}

}