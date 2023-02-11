package com.hanghae.thiscord_clone.Dto.request;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {

	@NotBlank(message = "email을 입력해주세요")
	private String email;

	@NotBlank(message = "password를 입력해주세요")
	private String password;

}