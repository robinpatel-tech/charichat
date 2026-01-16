package com.secureapp.Charichat.repository;

import com.secureapp.Charichat.entity.Chat;
import com.secureapp.Charichat.entity.GroupKey;
import com.secureapp.Charichat.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface GroupKeyRepository extends JpaRepository<GroupKey, UUID>{
    Optional<GroupKey> findByChatAndUser(Chat chat, User user);
}


//Secure group key lookup
//✔ Prevents duplicate keys
