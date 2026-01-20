package com.secureapp.Charichat.repository;

import com.secureapp.Charichat.entity.Chat;
import com.secureapp.Charichat.entity.ChatParticipant;
import com.secureapp.Charichat.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChatParticipantRepository
        extends JpaRepository<ChatParticipant, UUID> {
    List<ChatParticipant> findByUser(User user);

    List<ChatParticipant> findByChat(Chat chat);

    Optional<ChatParticipant> findByChatAndUser(Chat chat, User user);

    boolean existsByChatIdAndUserId(UUID chatId, UUID userId);
}
