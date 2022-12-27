package com.hanghae.thiscord_clone.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginInfo {
	private String username;
	private String hashTag;
	private String profilePic;
}
