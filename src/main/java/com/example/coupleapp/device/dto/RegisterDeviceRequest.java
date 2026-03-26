package com.example.coupleapp.device.dto;

import com.example.coupleapp.device.entity.DevicePlatform;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterDeviceRequest(
        @NotBlank String fcmToken,
        @NotNull DevicePlatform platform
) {
}
