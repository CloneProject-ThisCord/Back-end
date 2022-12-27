package com.hanghae.thiscord_clone.domain;

import com.hanghae.thiscord_clone.dto.request.ChatMessageRequestDto;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
public class ChatMessage implements Serializable {

	public enum MessageType {
		ENTER, TALK, QUIT
	}

	private MessageType type;
	private Long userId;
	private String chatId;
	private String roomId;
	private String createdAt;
	private String message;
	private String username;  //sender

	public ChatMessage(ChatMessageRequestDto requestDto) {
		this.type = requestDto.getType();
		this.userId = requestDto.getUserId();
		this.roomId = requestDto.getRoomId();
		this.message = requestDto.getMessage();
		this.username = requestDto.getUsername();
	}
}