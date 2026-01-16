package com.secureapp.Charichat.repository;

import com.secureapp.Charichat.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChatRepository extends JpaRepository<Chat, UUID> {
    @Query("""
        select c from Chat c
        join ChatParticipant p1 on p1.chat = c
        join ChatParticipant p2 on p2.chat = c
        where c.chatType = 'PRIVATE'
        and p1.user.id = :user1
        and p2.user.id = :user2
    """)
    Optional<Chat> findPrivateChatBetweenUsers(
            @Param("user1") UUID user1,
            @Param("user2") UUID user2
    );
    @Query("""
    SELECT DISTINCT c
    FROM Chat c
    JOIN ChatParticipant cp ON cp.chat = c
    WHERE cp.user.id = :userId
    ORDER BY c.createdAt DESC
""")
    List<Chat> findAllChatsForUser(@Param("userId") UUID userId);

}
