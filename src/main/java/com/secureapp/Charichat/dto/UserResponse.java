package com.secureapp.Charichat.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String email,
        String displayName,
        String publicKey,
        LocalDateTime createdAt,
        LocalDateTime lastSeen
) {}
