package com.hanghae.thiscord_clone.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanghae.thiscord_clone.dto.request.RoomRequestDto;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
public class ChatRoom implements Serializable {

	private String roomId;
	private String roomName;
	private String roomPic;

	public static ChatRoom create(RoomRequestDto requestDto) {
		ChatRoom chatRoom = new ChatRoom();
		chatRoom.roomId = UUID.randomUUID().toString();
		chatRoom.roomName = requestDto.getRoomName();
		chatRoom.roomPic = "https://discord99-buket.s3.ap-northeast-2.amazonaws.com/%E1%84%8E%E1%85%A2%E1%84%90%E1%85%B5%E1%86%BC%E1%84%87%E1%85%A1%E1%86%BC+%E1%84%8B%E1%85%A1%E1%84%8B%E1%85%B5%E1%84%8F%E1%85%A9%E1%86%AB.png";
		return chatRoom;
	}

}

