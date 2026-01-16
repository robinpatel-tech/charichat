package com.secureapp.Charichat.service;

import com.secureapp.Charichat.dto.*;
import com.secureapp.Charichat.entity.*;
import com.secureapp.Charichat.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final ChatParticipantRepository chatParticipantRepository;
    private final UserRepository userRepository;

    @Transactional
    public ChatResponse createPrivateChat(UUID currentUserId, UUID otherUserId) {

        // 1️⃣ Check if chat already exists
        return chatRepository
                .findPrivateChatBetweenUsers(currentUserId, otherUserId)
                .map(chat -> new ChatResponse(
                        chat.getId(),
                        chat.getChatType(),
                        chat.getCreatedAt()
                ))
                .orElseGet(() -> createNewPrivateChat(currentUserId, otherUserId));
    }
    private ChatResponse createNewPrivateChat(UUID currentUserId, UUID otherUserId) {

        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        User otherUser = userRepository.findById(otherUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Chat chat = Chat.builder()
                .chatType(ChatType.PRIVATE)
                .build();

        chatRepository.save(chat);

        chatParticipantRepository.save(
                ChatParticipant.builder()
                        .chat(chat)
                        .user(currentUser)
                        .role(ParticipantRole.MEMBER)
                        .build()
        );

        chatParticipantRepository.save(
                ChatParticipant.builder()
                        .chat(chat)
                        .user(otherUser)
                        .role(ParticipantRole.MEMBER)
                        .build()
        );

        return new ChatResponse(
                chat.getId(),
                chat.getChatType(),
                chat.getCreatedAt()
        );
    }





    // 🔹 GROUP CHAT
    @Transactional
    public ChatResponse createPrivateChatByEmail(String email, UUID otherUserId) {

        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return createPrivateChat(currentUser.getId(), otherUserId);
    }
    public ChatResponse createGroupChat(UUID creatorId, List<UUID> userIds) {

        User creator = userRepository.findById(creatorId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Chat chat = Chat.builder()
                .chatType(ChatType.GROUP)
                .build();

        chatRepository.save(chat);

        // Creator = ADMIN
        chatParticipantRepository.save(
                ChatParticipant.builder()
                        .chat(chat)
                        .user(creator)
                        .role(ParticipantRole.ADMIN)
                        .build()
        );

        // Members
        for (UUID userId : userIds) {
            if (userId.equals(creatorId)) continue;

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            chatParticipantRepository.save(
                    ChatParticipant.builder()
                            .chat(chat)
                            .user(user)
                            .role(ParticipantRole.MEMBER)
                            .build()
            );
        }

        return new ChatResponse(
                chat.getId(),
                chat.getChatType(),
                chat.getCreatedAt()
        );
    }
}
