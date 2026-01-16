package com.secureapp.Charichat.dto;

import com.secureapp.Charichat.entity.ChatType;

import java.time.LocalDateTime;
import java.util.UUID;

public record ChatResponse(
        UUID id,
        ChatType type,
        LocalDateTime createdAt
) {
}
