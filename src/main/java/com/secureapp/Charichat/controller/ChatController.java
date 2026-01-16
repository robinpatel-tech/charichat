package com.secureapp.Charichat.controller;

import com.secureapp.Charichat.dto.*;
import com.secureapp.Charichat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/chats")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    // 🔐 PRIVATE CHAT
    @PostMapping("/private")
    public ChatResponse createPrivateChat(
            Authentication authentication,
            @RequestBody CreatePrivateChatRequest request
    ) {
        String email = authentication.getName(); // from JWT
        return chatService.createPrivateChatByEmail(email, request.userId());
    }

    @GetMapping()
    public List<ChatResponse> getMyChats(Authentication authentication)
    {
        String email = authentication.getName();
        return chatService.getMyChats(email);
    }
    // 👥 GROUP CHAT
    @PostMapping("/group")
    public ChatResponse createGroupChat(
            Authentication authentication,
            @RequestBody CreateGroupChatRequest request
    ) {
        UUID creatorId = UUID.fromString(authentication.getName());
        return chatService.createGroupChat(creatorId, request.userIds());
    }
}
