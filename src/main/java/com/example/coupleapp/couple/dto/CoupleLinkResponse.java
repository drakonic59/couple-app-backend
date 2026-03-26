package com.example.coupleapp.couple.dto;

import java.time.Instant;
import java.util.UUID;

public record CoupleLinkResponse(
        UUID id,
        UUID user1Id,
        UUID user2Id,
        String status,
        Instant createdAt
) {
}
