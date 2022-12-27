package com.hanghae.thiscord_clone.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

	private Long id;
	private String email;
	private String nickname;
	private String profilePic;
	private String introduce;
	private String hashtag;
	private boolean status;

//	private LocalDateTime createdAt;
//	private LocalDateTime modifiedAt;
}