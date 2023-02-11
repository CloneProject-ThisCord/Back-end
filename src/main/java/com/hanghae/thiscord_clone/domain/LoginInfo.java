package com.hanghae.thiscord_clone.domain;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginInfo implements Serializable {
	private String username;
	private String hashTag;
	private String profilePic;

	public static LoginInfo insertUserInfo(User user) {
		LoginInfo loginInfo = new LoginInfo();
		loginInfo.username = user.getUsername();
		loginInfo.hashTag = user.getHashTag();
		loginInfo.profilePic = user.getProfilePic();
		return loginInfo;
	}
}
