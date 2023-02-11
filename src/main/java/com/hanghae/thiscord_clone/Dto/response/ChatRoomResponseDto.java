package com.hanghae.thiscord_clone.Dto.response;

import com.hanghae.thiscord_clone.domain.LoginInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ChatRoomResponseDto {
	private String roomId;
	private String roomName;
	private String roomPic;
	private List<LoginInfo> userInfoList = new ArrayList<>();
	private long userCount;

	@Builder
	public ChatRoomResponseDto(String roomId, String roomName, String roomPic, List<LoginInfo> userInfoList, long userCount) {
		this.roomId = roomId;
		this.roomName = roomName;
		this.roomPic = roomPic;
		this.userInfoList = userInfoList;
		this.userCount = userCount;
	}
}
