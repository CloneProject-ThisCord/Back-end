package com.hanghae.thiscord_clone.exception.custom;

import lombok.Getter;

@Getter
public class ChatException extends CommonException {

	public ChatException(ErrorCode errorCode) {
		super(errorCode);
	}
}
