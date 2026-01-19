package com.secureapp.Charichat.service;

import com.secureapp.Charichat.dto.MessageRequest;
import com.secureapp.Charichat.dto.MessageResponse;
import com.secureapp.Charichat.entity.Chat;
import com.secureapp.Charichat.entity.Message;
import com.secureapp.Charichat.entity.MessageStatus;
import com.secureapp.Charichat.entity.User;
import com.secureapp.Charichat.repository.ChatParticipantRepository;
import com.secureapp.Charichat.repository.ChatRepository;
import com.secureapp.Charichat.repository.MessageRepository;
import com.secureapp.Charichat.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final ChatParticipantRepository chatParticipantRepository;
    private final UserRepository userRepository;


    @Transactional
    public MessageResponse sendMessage(
            String email,
            MessageRequest request
    ) {


        // 1️⃣ Sender
        User sender = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2️⃣ Chat
        Chat chat = chatRepository.findById(request.chatId())
                .orElseThrow(() -> new RuntimeException("Chat not found"));

        // 3️⃣ Membership check
        boolean allowed =
                chatParticipantRepository.existsByChatIdAndUserId(
                        chat.getId(),
                        sender.getId()
                );

        if (!allowed) {
            throw new RuntimeException("Access denied to this chat");
        }

        // 4️⃣ Save message
        Message message = Message.builder()
                .chat(chat)
                .sender(sender)
                .cipherText(request.cipherText())
                .status(MessageStatus.SENT)
                .build();


        Message savedMessage = messageRepository.saveAndFlush(message);

        return new MessageResponse(
                savedMessage.getId(),
                savedMessage.getSender().getId(),
                savedMessage.getChat().getId(),
                savedMessage.getSender().getDisplayName(),
                savedMessage.getCipherText(),
                savedMessage.getCreatedAt()
        );


    }
}
