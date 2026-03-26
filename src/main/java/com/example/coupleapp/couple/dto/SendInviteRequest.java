package com.example.coupleapp.couple.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SendInviteRequest(
        @NotBlank @Email String partnerEmail
) {
}
