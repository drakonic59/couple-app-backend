package com.example.coupleapp.device.dto;

import jakarta.validation.constraints.NotBlank;

public record UnregisterDeviceRequest(@NotBlank String fcmToken) {
}
