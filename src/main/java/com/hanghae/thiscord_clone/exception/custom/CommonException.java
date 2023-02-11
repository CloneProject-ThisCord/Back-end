package com.hanghae.thiscord_clone.exception.custom;

import lombok.Getter;

@Getter
public abstract class CommonException extends RuntimeException {

	protected String msg;
	protected int statusCode;

	public CommonException(ErrorCode errorCode) {
		this.msg = errorCode.getMsg();
		this.statusCode = errorCode.getStatusCode();
	}
}
