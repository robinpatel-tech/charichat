package com.secureapp.Charichat.controller;

import com.secureapp.Charichat.dto.MessageRequest;
import com.secureapp.Charichat.dto.MessageResponse;
import com.secureapp.Charichat.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    public MessageResponse sendMessage(
            Authentication authentication,
            @RequestBody MessageRequest request
    ) {
        String email = authentication.getName();
        return messageService.sendMessage(email, request);
    }
}