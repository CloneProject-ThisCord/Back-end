package com.hanghae.thiscord_clone.controller;

import com.hanghae.thiscord_clone.domain.ChatMessage;
import com.hanghae.thiscord_clone.repository.ChatMessageRepository;
import com.hanghae.thiscord_clone.service.ChatMessageService;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatMessageController {
	private final ChatMessageService chatMessageService;
	private final ChatMessageRepository chatMessageRepository;

	@MessageMapping("/chat/message")
	public void message(com.hanghae.thiscord_clone.dto.request.ChatMessageRequestDto requestDto) {
		LocalDateTime now = LocalDateTime.now();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		chatMessageService.save(requestDto, format.format(now));

		log.info("chatMessage Type: {}" , requestDto.getType());
		ChatMessage message = ChatMessage.builder()
			.type(requestDto.getType())
			.roomId(requestDto.getRoomId())
			.username(requestDto.getUsername())
			.message(requestDto.getMessage())
			.userId(requestDto.getUserId())
			.createdAt(format.format(now))
			.build();
		chatMessageService.sendChatMessage(message);
		log.info("Receive Message {}", "메세지 수신 완료");
	}

	@GetMapping("/api/rooms/{roomId}/messages")
	public List<ChatMessage> getMessages(@PathVariable String roomId) {
		return chatMessageRepository.chatList(roomId);
	}
}
