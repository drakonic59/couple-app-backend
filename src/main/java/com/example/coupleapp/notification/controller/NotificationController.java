package com.example.coupleapp.notification.controller;

import com.example.coupleapp.common.security.CurrentUserService;
import com.example.coupleapp.notification.dto.NotificationResponse;
import com.example.coupleapp.notification.service.NotificationService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final CurrentUserService currentUserService;
    private final NotificationService notificationService;

    public NotificationController(CurrentUserService currentUserService, NotificationService notificationService) {
        this.currentUserService = currentUserService;
        this.notificationService = notificationService;
    }

    @GetMapping
    public List<NotificationResponse> list() {
        return notificationService.listForUser(currentUserService.userId());
    }
}
