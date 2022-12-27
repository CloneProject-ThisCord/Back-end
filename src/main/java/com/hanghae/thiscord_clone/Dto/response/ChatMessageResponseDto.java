package com.hanghae.thiscord_clone.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class ChatMessageResponseDto {
	private String roomId;
	private Long userId;
	private String message;
	private String username;
	private String createdAt;

	public ChatMessageResponseDto(String roomId, Long userId, String message, String username, String createdAt) {
		this.roomId = roomId;
		this.userId = userId;
		this.message = message;
		this.username = username;
		this.createdAt = createdAt;
	}
}
