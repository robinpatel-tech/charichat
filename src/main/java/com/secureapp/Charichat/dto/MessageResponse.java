package com.secureapp.Charichat.dto;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

public record MessageResponse(
        UUID id,
        UUID senderId,
        UUID chatId,
        String senderName,
        String CipherText,
        LocalDateTime createdAt
) {}
