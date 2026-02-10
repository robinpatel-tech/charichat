package com.secureapp.Charichat.controller;


import com.secureapp.Charichat.dto.MessageResponse;
import com.secureapp.Charichat.dto.SendMessageWsRequest;
import com.secureapp.Charichat.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.send")
    public void sendMessage(
            SendMessageWsRequest request,
            Principal principal
    ) {
        String email = principal.getName();

        MessageResponse saved =
                messageService.sendMessage(
                        email,
                        request.chatId(),
                        request.cipherText()
                );

        messagingTemplate.convertAndSend(
                "/topic/chat." + request.chatId(),
                saved
        );
    }
}