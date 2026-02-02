package com.secureapp.Charichat.entity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name ="users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

@Id
@GeneratedValue(strategy = GenerationType.UUID)
// MySQL compatibility fix: Changed from "uuid" to "BINARY(16)"
@Column(name = "id", columnDefinition = "BINARY(16)", updatable = false, nullable = false)
private UUID id;

    @Column(nullable = false , unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false )
    private String passwordHash;

    @Column(name = "display_name")
    private String displayName;

@Column(name = "public_key", columnDefinition = "TEXT", nullable = false)
private String publicKey;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "last_seen")
    private LocalDateTime lastSeen;
}
