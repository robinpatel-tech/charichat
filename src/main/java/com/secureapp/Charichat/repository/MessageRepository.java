package com.secureapp.Charichat.repository;

import com.secureapp.Charichat.entity.Chat;
import com.secureapp.Charichat.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID>{
    List<Message> findByChatOrderByCreatedAtAsc(Chat chat);
    List<Message> findByChatIdOrderByCreatedAtAsc(UUID chatId);
}
