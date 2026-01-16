package com.secureapp.Charichat.controller;

import com.secureapp.Charichat.dto.*;
import com.secureapp.Charichat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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

//    @PostMapping("/private")
//    public ChatResponse createPrivateChat(
//            Authentication authentication,
//            @RequestBody CreatePrivateChatRequest request
//    ) {
//        String email = authentication.getName(); // ✅ email from JWT
//        return chatService.createPrivateChatByEmail(email, request.userId());
//    }



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
