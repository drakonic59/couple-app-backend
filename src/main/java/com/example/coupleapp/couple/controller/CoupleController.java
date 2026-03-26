package com.example.coupleapp.couple.controller;

import com.example.coupleapp.common.security.CurrentUserService;
import com.example.coupleapp.couple.dto.CoupleLinkResponse;
import com.example.coupleapp.couple.dto.SendInviteRequest;
import com.example.coupleapp.couple.service.CoupleService;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/couple")
public class CoupleController {

    private final CoupleService coupleService;
    private final CurrentUserService currentUserService;

    public CoupleController(CoupleService coupleService, CurrentUserService currentUserService) {
        this.coupleService = coupleService;
        this.currentUserService = currentUserService;
    }

    @PostMapping("/invite")
    public CoupleLinkResponse sendInvite(@Valid @RequestBody SendInviteRequest request) {
        return coupleService.sendInvite(currentUserService.userId(), request);
    }

    @PostMapping("/invite/{inviteId}/accept")
    public CoupleLinkResponse acceptInvite(@PathVariable UUID inviteId) {
        return coupleService.acceptInvite(currentUserService.userId(), inviteId);
    }

    @GetMapping("/active")
    public CoupleLinkResponse activeLink() {
        return coupleService.activeLink(currentUserService.userId());
    }
}
