package com.example.coupleapp.auth.dto;

import com.example.coupleapp.device.dto.RegisterDeviceRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank @Email String email,
        @NotBlank @Size(min = 8, max = 120) String password,
        @NotBlank @Size(max = 120) String displayName,
        @NotBlank @Size(max = 120) String firstName,
        @NotBlank @Size(max = 120) String lastName,
        @Size(max = 255) String address,
        @NotNull @Valid RegisterDeviceRequest device
) {
}
