package com.hanghae.thiscord_clone.controller;

import com.hanghae.thiscord_clone.Dto.response.ChatRoomResponseDto;
import com.hanghae.thiscord_clone.domain.ChatRoom;
import com.hanghae.thiscord_clone.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ChatRoomController {

    private final ChatRoomRepository chatRoomRepository;

    //채팅방 목록 조회
    @GetMapping("/rooms")
    public List<ChatRoom> findAllRoom() {
        return chatRoomRepository.findAllRoom();
    }


    //채팅방 생성
    @PostMapping("/rooms")
    public ChatRoomResponseDto createRoom(@RequestBody com.hanghae.thiscord_clone.Dto.request.RoomRequestDto requestDto) {
        return chatRoomRepository.createChatRoom(requestDto);
    }

    //특정 채팅방 조회
    @GetMapping("/rooms/{roomId}")
    public ChatRoomResponseDto roomInfo (@PathVariable String roomId) {
        return chatRoomRepository.findRoomById(roomId);
    }
}

