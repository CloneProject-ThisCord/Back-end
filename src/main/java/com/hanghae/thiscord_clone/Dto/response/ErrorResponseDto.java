package com.hanghae.thiscord_clone.Dto.response;

import com.hanghae.thiscord_clone.exception.custom.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ErrorResponseDto {
	private String msg;
	private int statusCode;

	public ErrorResponseDto(ErrorCode errorCode) {
		this.msg = errorCode.getMsg();
		this.statusCode = errorCode.getStatusCode();
	}
}
