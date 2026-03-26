package com.example.coupleapp.couple.dto;

import com.example.coupleapp.couple.entity.CoupleStatus;

public record CoupleLinkResponse(
        Long id,
        Long user1Id,
        Long user2Id,
        CoupleStatus status
) {
}
