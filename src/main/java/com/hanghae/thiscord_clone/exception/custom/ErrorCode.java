package com.hanghae.thiscord_clone.exception.custom;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import lombok.Getter;

@Getter
public enum ErrorCode {

	/* 400 BAD_REQUEST */
	USER_NOT_FOUND("회원 정보를 찾을 수 없습니다.", BAD_REQUEST.value()),
	DUP_USER_EMAIL("중복된 이메일 입니다.", BAD_REQUEST.value()),
	MISMATCH_PASSWORD("비밀번호가 일치하지 않습니다.", BAD_REQUEST.value()),
	CANNOT_MAKE_ROOM("채팅방 생성에 실패하였습니다.", BAD_REQUEST.value()),
	INVALID_TOKEN("토큰이 유효하지 않습니다.", BAD_REQUEST.value()),
	EXPIRED_TOKEN("만료된 토큰입니다.", BAD_REQUEST.value()),
	UNSUPPORTED_TOKEN("지원되지 않는 토큰입니다.", BAD_REQUEST.value()),

	/* 401 UNAUTHORIZED */
	UNAUTHORIZED_ERROR("인증되지 않았습니다.", UNAUTHORIZED.value()),

	/*403 FORBIDDEN */
	FORBIDDEN_ERROR("접근이 거부되었습니다", FORBIDDEN.value());

	private final String msg;
	private final int statusCode;

	ErrorCode(String msg, int statusCode) {
		this.msg = msg;
		this.statusCode = statusCode;
	}
}
