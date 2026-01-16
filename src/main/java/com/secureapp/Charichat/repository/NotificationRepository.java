package com.secureapp.Charichat.repository;

import com.secureapp.Charichat.entity.Notification;
import com.secureapp.Charichat.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    List<Notification> findByUserAndIsReadFalse(User user);
}


//Clean unread notification handling