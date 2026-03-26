package com.example.coupleapp.user.dto;

import java.util.UUID;

public record MeResponse(
        UUID id,
        String email,
        String displayName,
        String firstName,
        String lastName,
        String address,
        String status
) {
}
