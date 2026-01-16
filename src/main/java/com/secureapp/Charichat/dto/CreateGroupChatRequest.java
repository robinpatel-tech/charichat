package com.secureapp.Charichat.dto;

import java.sql.ClientInfoStatus;
import java.util.List;
import java.util.UUID;

public record CreateGroupChatRequest(
        String name , List<UUID> userIds
) {}
