package com.secureapp.Charichat.dto;

import java.util.UUID;

public record MessageRequest(
        UUID chatId,
        String cipherText
) {}
