package com.hanghae.thiscord_clone.exception.custom;

import lombok.Getter;

@Getter
public class CustomSecurityException extends CommonException{

	public CustomSecurityException(ErrorCode errorCode) {
		super(errorCode);
	}
}
