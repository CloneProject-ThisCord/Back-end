package com.hanghae.thiscord_clone.Dto.response;

import lombok.Getter;

@Getter
public class LoginResponseDto {

    private Long userId;
    private String username;
    private String hasTag;
    private String profilePic;

    public LoginResponseDto(Long userId, String username, String hasTag, String profilePic) {
        this.userId = userId;
        this.username = username;
        this.hasTag = hasTag;
        this.profilePic = profilePic;
    }

}
