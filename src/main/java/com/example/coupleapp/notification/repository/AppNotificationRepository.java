package com.example.coupleapp.notification.repository;

import com.example.coupleapp.notification.entity.AppNotification;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppNotificationRepository extends JpaRepository<AppNotification, Long> {

    List<AppNotification> findTop50ByUserIdOrderByCreatedAtDesc(UUID userId);
}
