package com.hanghae.thiscord_clone.Dto.response;

import lombok.Getter;

@Getter
public class LoginResponseDto {

    private String username;
    private String hasTag;
    private String profilePic;

    public LoginResponseDto(String username, String hasTag, String profilePic) {
        this.username = username;
        this.hasTag = hasTag;
        this.profilePic = profilePic;
    }

}
