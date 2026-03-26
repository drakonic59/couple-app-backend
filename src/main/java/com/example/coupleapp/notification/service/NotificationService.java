package com.example.coupleapp.notification.service;

import com.example.coupleapp.notification.dto.NotificationResponse;
import com.example.coupleapp.notification.repository.AppNotificationRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NotificationService {

    private final AppNotificationRepository repository;

    public NotificationService(AppNotificationRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<NotificationResponse> listForUser(UUID userId) {
        return repository.findTop50ByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(n -> new NotificationResponse(n.getId(), n.getType(), n.getTitle(), n.getBody(), n.getReadAt(), n.getCreatedAt()))
                .toList();
    }
}
