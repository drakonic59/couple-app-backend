package com.example.coupleapp.user.controller;

import com.example.coupleapp.common.security.CurrentUserService;
import com.example.coupleapp.user.dto.MeResponse;
import com.example.coupleapp.user.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/me")
public class MeController {

    private final CurrentUserService currentUserService;
    private final UserService userService;

    public MeController(CurrentUserService currentUserService, UserService userService) {
        this.currentUserService = currentUserService;
        this.userService = userService;
    }

    @GetMapping
    public MeResponse me() {
        return userService.me(currentUserService.userId());
    }
}
