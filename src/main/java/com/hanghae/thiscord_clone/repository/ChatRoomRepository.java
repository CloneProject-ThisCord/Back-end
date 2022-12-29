package com.hanghae.thiscord_clone.repository;

import com.hanghae.thiscord_clone.Dto.request.RoomRequestDto;
import com.hanghae.thiscord_clone.domain.ChatRoom;
import com.hanghae.thiscord_clone.domain.LoginInfo;
import com.hanghae.thiscord_clone.domain.User;
import java.util.List;
import java.util.Optional;
import javax.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ChatRoomRepository {

	private static final String CHAT_ROOMS = "CHAT_ROOM"; // 채팅방 저장
	public static final String USER_COUNT = "USER_COUNT"; // 채팅방에 입장한 클라이언트수 저장
	public static final String ENTER_INFO = "ENTER_INFO"; // 채팅방에 입장한 클라이언트의 sessionId와 채팅룸 id를 맵핑한 정보 저장
	public static final String USER_INFO = "USER_INFO"; // 채팅방에 입장한 클라이언트의 정보와 채팅룸 id를 맵핑한 정보 저장

	@Resource(name = "redisTemplate")
	private HashOperations<String, String, ChatRoom> hashOpsChatRoom;
	@Resource(name = "redisTemplate")
	private HashOperations<String, String, LoginInfo> hashOpsLoginInfo;
	@Resource(name = "redisTemplate")
	private HashOperations<String, String, String> hashOpsEnterInfo;
	@Resource(name = "redisTemplate")
	private ValueOperations<String, String> valueOps;

	// 모든 채팅방 조회
	public List<ChatRoom> findAllRoom() {
		return hashOpsChatRoom.values(CHAT_ROOMS);
	}

	// 특정 채팅방 조회
	public ChatRoom findRoomById(String roomId) {
		return hashOpsChatRoom.get(CHAT_ROOMS, roomId);
//		return ChatRoomResponseDto.builder()
//			.roomId(chatRoom.getRoomId())
//			.roomName(chatRoom.getRoomName())
//			.roomPic(chatRoom.getRoomPic())
//			.userCount(getUserCount(chatRoom.getRoomId()))
//			.userInfoList(getUserInfoList(chatRoom.getRoomId())).build();
	}

	// 채팅방 생성 : 서버간 채팅방 공유를 위해 redis hash 에 저장한다. -> 채팅방 지워지지 않음
	public ChatRoom createChatRoom(RoomRequestDto roomRequestDto) {
		ChatRoom chatRoom = ChatRoom.create(roomRequestDto);
		hashOpsChatRoom.put(CHAT_ROOMS, chatRoom.getRoomId(), chatRoom);
		return ChatRoom.builder()
			.roomId(chatRoom.getRoomId())
			.roomName(chatRoom.getRoomName())
			.roomPic(chatRoom.getRoomPic())
			.build();
//		return ChatRoomResponseDto.builder()
//			.roomId(chatRoom.getRoomId().substring(7))
//			.roomName(chatRoom.getRoomName())
//			.roomPic(chatRoom.getRoomPic())
//			.build();
	}

	// 유저가 입장한 roomId 와 유저 sessionId 맵핑 정보 저장
	public void setUserEnterInfo(String sessionId, String roomId) {
		hashOpsEnterInfo.put(ENTER_INFO, sessionId, roomId);
	}

	// roomId 로 채팅방에 입장한 유저 정보 저장
	public void setUserInfo(String roomId, User user) {
		String hashKey = roomId + user.getId();
		LoginInfo loginInfo = LoginInfo.builder()
			.username(user.getUsername())
			.profilePic(user.getProfilePic())
			.hashTag(user.getHashTag())
			.build();
		hashOpsLoginInfo.put(USER_INFO , hashKey, loginInfo);
	}

	// 채팅방에서 퇴장한 유저 정보 삭제
	public void removeUserInfo(String roomId, User user) {
		String hashKey = roomId + user.getId();
		hashOpsLoginInfo.delete(USER_INFO, hashKey);
	}

	// roomId 로 채팅방에 입장한 유저 정보 조회
	public List<LoginInfo> getUserInfoList(String roomId) {
		List<LoginInfo> userInfoList = hashOpsLoginInfo.values(roomId);
		log.info("로그인 정보 리스트 {}", userInfoList);
		return userInfoList;
	}

	// 유저 세션으로 입장해 있는 채팅방 ID 조회
	public String getUserEnterRoomId(String sessionId) {
		return hashOpsEnterInfo.get(ENTER_INFO, sessionId);
	}

	// 유저 세션정보와 맵핑된 채팅방 id 삭제
	public void removeUserEnterInfo(String sessionId) {
		hashOpsEnterInfo.delete(ENTER_INFO, sessionId);
	}

	// 채팅방 유저수 조회
	public long getUserCount(String roomId) {
		return Long.parseLong(Optional.ofNullable(valueOps.get(USER_COUNT + "_" + roomId)).orElse("0"));
	}

	// 채팅방에 입장한 유저수 +1
	public void plusUserCount(String roomId) {
		valueOps.increment(USER_COUNT + "_" + roomId);
	}

	// 채팅방에 입장한 유저수 -1
	public void minusUserCount(String roomId) {
		Optional.ofNullable(valueOps.decrement(USER_COUNT + "_" + roomId))
			.filter(count -> count > 0);
	}


	//채팅방 이름 수정
//	public ChatRoom modifyChatRoom(String roomId, String name) {
//		hashOpsChatRoom.put(CHAT_ROOMS, roomId, ChatRoom.builder()
//			.roomId(roomId)
//			.roomName(name)
//			.build());
//		return hashOpsChatRoom.get(CHAT_ROOMS, roomId);
//	}
}
