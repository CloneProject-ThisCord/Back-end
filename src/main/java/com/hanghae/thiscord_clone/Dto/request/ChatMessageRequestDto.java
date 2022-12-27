package com.hanghae.thiscord_clone.dto.request;

import com.hanghae.thiscord_clone.domain.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessageRequestDto {
	private ChatMessage.MessageType type; // 메시지 타입

	private Long userId;  //sender id

	private String roomId;  //ChatRoom id

	private String roomName;  //ChatRoomName

	private String message; //message
	private String username; //sender

}
