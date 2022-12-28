package com.hanghae.thiscord_clone.service;

import com.hanghae.thiscord_clone.domain.ChatMessage;
import com.hanghae.thiscord_clone.Dto.request.ChatMessageRequestDto;
import com.hanghae.thiscord_clone.repository.ChatMessageRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatMessageService {
	private final RedisTemplate redisTemplate;
	private final ChatMessageRepository chatMessageRepository;
	private final ChannelTopic channelTopic;

	public void save(ChatMessageRequestDto requestDto, String time) {
		ChatMessage chatMessage = new ChatMessage();
		chatMessage.setUsername(requestDto.getUsername());
		chatMessage.setMessage(requestDto.getMessage());
		chatMessage.setRoomId(requestDto.getRoomId());
		chatMessage.setType(requestDto.getType());
		chatMessage.setChatId(UUID.randomUUID().toString());
		chatMessage.setUserId(requestDto.getUserId());
		chatMessage.setCreatedAt(time);
		chatMessageRepository.save(chatMessage);
	}

	public void sendChatMessage(ChatMessage chatMessage) {
		if (ChatMessage.MessageType.ENTER.equals(chatMessage.getType())) {
			chatMessage.setMessage(chatMessage.getUsername() + "님이 방에 입장했습니다.");
			chatMessage.setUsername("[알림]");
		} else if (ChatMessage.MessageType.QUIT.equals(chatMessage.getType())) {
			chatMessage.setMessage(chatMessage.getUsername() + "님이 방에서 나갔습니다.");
			chatMessage.setUsername("[알림]");
		}
		redisTemplate.convertAndSend(channelTopic.getTopic(), chatMessage);
	}
}

