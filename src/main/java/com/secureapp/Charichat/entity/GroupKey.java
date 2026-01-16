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
    @Column(columnDefinition = "uuid")
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
