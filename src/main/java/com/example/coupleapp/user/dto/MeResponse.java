package com.example.coupleapp.user.dto;

import com.example.coupleapp.user.entity.UserStatus;

public record MeResponse(
        Long id,
        String email,
        String displayName,
        UserStatus status
) {
}
