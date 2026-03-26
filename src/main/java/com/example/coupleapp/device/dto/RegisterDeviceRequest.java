package com.example.coupleapp.device.dto;

import com.example.coupleapp.device.entity.DevicePlatform;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterDeviceRequest(
        @NotBlank @Size(max = 400) String fcmToken,
        @NotNull DevicePlatform platform,
        @NotBlank @Size(max = 40) String appVersion
) {
}
