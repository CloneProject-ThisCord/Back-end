package com.hanghae.thiscord_clone.repository;


import com.hanghae.thiscord_clone.domain.ChatMessage;
import com.hanghae.thiscord_clone.dto.response.ChatMessageResponseDto;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ChatMessageRepository {

    private final RedisTemplate<String, Object> redisTemplate;
//	private static final String CHAT_MESSAGES = "CHAT_MESSAGES";

    @Resource(name = "redisTemplate")
    private HashOperations<String, String, ChatMessage> hashOpsChatMessage;
    //	@Resource(name = "redisTemplate")
//	private ValueOperations<String, ChatMessage> valueOps;
    @Resource(name = "redisTemplate")
    private HashOperations<String, String, List<ChatMessage>> opsHashChatMessages;

    @PostConstruct
    private void init() {
        opsHashChatMessages = redisTemplate.opsForHash();
    }

    public List<ChatMessage> chatList(String roomId) {
        List<ChatMessage> chatMessages = hashOpsChatMessage.values(roomId);
        log.info("메시지 리스트 {}", chatMessages);
        return chatMessages;
    }

    public ChatMessageResponseDto save(ChatMessage chatMessage) {
        log.info("chatMessage: " + chatMessage);
        hashOpsChatMessage.put(chatMessage.getRoomId(), chatMessage.getChatId(), chatMessage);

        return ChatMessageResponseDto.builder()
            .message(chatMessage.getMessage())
            .roomId(chatMessage.getRoomId())
            .userId(chatMessage.getUserId())
            .username(chatMessage.getUsername())
            .build();
    }
}