package com.hanghae.thiscord_clone.pubsub;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanghae.thiscord_clone.domain.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisSubscriber {

    private final ObjectMapper objectMapper;
    private final SimpMessageSendingOperations messagingTemplate;

    /**
     * Redis 에서 메시지가 발행(publish)되면 대기하고 있던 Redis Subscriber 가 해당 메시지를 처리
     */
    public void sendMessage(String message) {
        try {
            // ChatMessage 객채로 맵핑
            ChatMessage chatMessage = objectMapper.readValue(message, ChatMessage.class);

            // 채팅방을 구독한 클라이언트에게 메시지 발송
            System.out.println("채팅방을 구독한 클라이언트에게 메시지 발송: " + chatMessage);
            messagingTemplate.convertAndSend("/sub/chat/room/" + chatMessage.getRoomId(), chatMessage);
        } catch (Exception e) {
            log.error("Exception {}", e);
        }
    }
}
