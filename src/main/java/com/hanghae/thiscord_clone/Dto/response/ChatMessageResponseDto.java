package com.hanghae.thiscord_clone.Dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageResponseDto {
	private String roomId;
	private Long userId;
	private String message;
	private String username;
	private String createdAt;

//	public ChatMessageResponseDto(String roomId, Long userId, String message, String username, String createdAt) {
//		this.roomId = roomId;
//		this.userId = userId;
//		this.message = message;
//		this.username = username;
//		this.createdAt = createdAt;
//	}
}
