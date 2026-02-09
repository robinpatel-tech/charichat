package com.secureapp.Charichat.dto;

import java.util.UUID;

public record SendMessageWsRequest (
  UUID chatId,
  String cipherText
){}
