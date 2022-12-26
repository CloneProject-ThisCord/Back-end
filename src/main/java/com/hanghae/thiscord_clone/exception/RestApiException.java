package com.hanghae.thiscord_clone.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestApiException {
	private String msg;
	private int statusCode;
}
