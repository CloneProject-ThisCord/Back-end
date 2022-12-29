package com.hanghae.thiscord_clone.dto.response;

import com.hanghae.discord_clone.entity.ChatMessage;
import com.hanghae.discord_clone.entity.User;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomResponseDto {

	private Long roomId;
	private String roomName;
	private String imageUrl;
	private List<User> userList;
	private List<ChatMessage> chatMessageList;
	private Long onlineUser;
	private Long offlineUser;
}