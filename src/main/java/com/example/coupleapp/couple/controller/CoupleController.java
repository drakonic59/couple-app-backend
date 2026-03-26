package com.example.coupleapp.couple.controller;

import com.example.coupleapp.common.security.CurrentUserService;
import com.example.coupleapp.couple.dto.CoupleLinkResponse;
import com.example.coupleapp.couple.dto.SendInviteRequest;
import com.example.coupleapp.couple.service.CoupleService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/couple")
public class CoupleController {

    private final CurrentUserService currentUserService;
    private final CoupleService coupleService;

    public CoupleController(CurrentUserService currentUserService, CoupleService coupleService) {
        this.currentUserService = currentUserService;
        this.coupleService = coupleService;
    }

    @PostMapping("/invites")
    public CoupleLinkResponse sendInvite(@Valid @RequestBody SendInviteRequest request) {
        return coupleService.sendInvite(currentUserService.userId(), request);
    }

    @PostMapping("/invites/{inviteId}/accept")
    public CoupleLinkResponse acceptInvite(@PathVariable Long inviteId) {
        return coupleService.acceptInvite(currentUserService.userId(), inviteId);
    }

    @GetMapping("/active")
    public CoupleLinkResponse active() {
        return coupleService.activeLink(currentUserService.userId());
    }
}
