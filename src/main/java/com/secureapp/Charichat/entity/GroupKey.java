package com.secureapp.Charichat.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "group_key",uniqueConstraints = @UniqueConstraint(columnNames = {"chat_id", "user_id"}))
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupKey {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    // MySQL compatibility fix: Changed "uuid" to "BINARY(16)"
    @Column(name = "id", columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id", nullable = false)
    private Chat chat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "encrypted_group_key", columnDefinition = "TEXT", nullable = false)
    private String encryptedGroupKey;
}
