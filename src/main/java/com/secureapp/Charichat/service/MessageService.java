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

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final ChatParticipantRepository chatParticipantRepository;
    private final UserRepository userRepository;


@Transactional
public MessageResponse sendMessage(
        String senderEmail,
        UUID chatId,
        String cipherText
) {
    User sender = userRepository.findByEmail(senderEmail)
            .orElseThrow(() -> new RuntimeException("User not found"));

    //Authorization check
    boolean isParticipant =
            chatParticipantRepository.existsByChatIdAndUserId(chatId, sender.getId());

    if (!isParticipant) {
        throw new RuntimeException("Access denied to this chat");
    }

    Chat chat = chatRepository.findById(chatId)
            .orElseThrow(() -> new RuntimeException("Chat not found"));

    Message message = Message.builder()
            .chat(chat)
            .sender(sender)
            .cipherText(cipherText)
            .status(MessageStatus.SENT)
            .build();

    Message saved = messageRepository.saveAndFlush(message);

    return new MessageResponse(
            saved.getId(),
            sender.getId(),
            chat.getId(),
            sender.getDisplayName(),
            saved.getCipherText(),
            saved.getCreatedAt()
    );
}

}
