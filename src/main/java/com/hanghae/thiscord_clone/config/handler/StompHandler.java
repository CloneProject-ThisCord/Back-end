package com.hanghae.thiscord_clone.config.handler;

import com.hanghae.thiscord_clone.domain.ChatMessage;
import com.hanghae.thiscord_clone.domain.User;
import com.hanghae.thiscord_clone.repository.ChatRoomRepository;
import com.hanghae.thiscord_clone.security.UserDetailsImpl;
import com.hanghae.thiscord_clone.security.jwt.JwtUtil;
import com.hanghae.thiscord_clone.service.ChatMessageService;
import com.hanghae.thiscord_clone.service.ChatRoomService;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class StompHandler implements ChannelInterceptor {

	@Autowired
	private final JwtUtil jwtUtil;
	@Autowired
	private final ChatRoomService chatRoomService;
	@Autowired
	private final ChatRoomRepository chatRoomRepository;
	@Autowired
	private final ChatMessageService chatMessageService;


	// websocket 을 통해 들어온 요청이 처리 되기전 실행
	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

		if (StompCommand.CONNECT == accessor.getCommand()) { // websocket 연결요청
			String jwtToken = accessor.getFirstNativeHeader("Authorization").substring(7);
			log.info("connect {}", jwtToken);
			// Header 의 jwt token 검증
			jwtUtil.validateToken(jwtToken);
		} else if (StompCommand.SUBSCRIBE == accessor.getCommand()) { // 채팅룸 구독요청
			User user = ((UserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
//			String name = Optional.ofNullable((Principal) message.getHeaders()
//                    .get("simpUser")).map(Principal::getName).orElse("UnknownUser");

			// header 정보에서 구독 destination 정보를 얻고, roomId를 추출
			String roomId = chatRoomService.getRoomId(
				Optional.ofNullable((String) message.getHeaders().get("simpDestination")).orElse("InvalidRoomId"));

			// 채팅방에 들어온 클라이언트 sessionId를 roomId와 맵핑해 놓는다.(나중에 특정 세션이 어떤 채팅방에 들어가 있는지 알기 위함)
			String sessionId = (String) message.getHeaders().get("simpSessionId");
			chatRoomRepository.setUserEnterInfo(sessionId, roomId);

			// 채팅방의 인원수를 +1한다.
			chatRoomRepository.plusUserCount(roomId);

			// 입장한 유저 정보를 저장하고, 입장 메시지를 채팅방에 발송 (redis publish)
			chatRoomRepository.setUserInfo(roomId, user);
			chatMessageService.sendChatMessage(
				ChatMessage.builder()
					.type(ChatMessage.MessageType.ENTER)
					.roomId(roomId)
					.username(user.getUsername()).build());

			log.info("subscribed {}, {}", user.getUsername(), roomId);

		} else if (StompCommand.DISCONNECT == accessor.getCommand()) { // Websocket 연결 종료
			User user = ((UserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
//			String name = Optional.ofNullable((Principal) message.getHeaders().get("simpUser")).map(Principal::getName).orElse("UnknownUser");

			// 연결이 종료된 클라이언트 session id 로 채팅방 id 가져오기
			String sessionId = (String) message.getHeaders().get("simpSessionId");
			String roomId = chatRoomRepository.getUserEnterRoomId(sessionId);

			// 채팅방의 인원수를 -1한다.
			chatRoomRepository.minusUserCount(roomId);

			// 퇴장한 유저 정보를 삭제하고, 클라이언트 퇴장 메시지를 채팅방에 발송(redis publish)
			chatRoomRepository.removeUserInfo(roomId, user);
			chatMessageService.sendChatMessage(
				ChatMessage.builder()
					.type(ChatMessage.MessageType.QUIT)
					.roomId(roomId)
					.username(user.getUsername())
					.build());

			// 퇴장한 클라이언트의 roomId 맵핑 정보를 삭제한다.
			chatRoomRepository.removeUserEnterInfo(sessionId);
			log.info("DISCONNECTED {}, {}", sessionId, roomId);
		}
		return message;
	}
}
