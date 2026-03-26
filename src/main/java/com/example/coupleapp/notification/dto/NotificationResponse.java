package com.example.coupleapp.notification.dto;

import java.time.Instant;

public record NotificationResponse(
        Long id,
        String type,
        String title,
        String body,
        Instant readAt,
        Instant createdAt
) {
}
