package com.hanghae.thiscord_clone.dto.response;

import com.hanghae.thiscord_clone.domain.ChatMessage;
import com.hanghae.thiscord_clone.domain.LoginInfo;
import com.hanghae.thiscord_clone.domain.User;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
