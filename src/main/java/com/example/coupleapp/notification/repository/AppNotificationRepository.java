package com.example.coupleapp.notification.repository;

import com.example.coupleapp.notification.entity.AppNotification;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppNotificationRepository extends JpaRepository<AppNotification, Long> {

    List<AppNotification> findTop50ByUserIdOrderByCreatedAtDesc(Long userId);
}
