package com.hanghae.thiscord_clone.exception.custom;

import lombok.Getter;

@Getter
public class UserException extends CommonException {

	public UserException(ErrorCode errorCode) {
		super(errorCode);
	}
}
