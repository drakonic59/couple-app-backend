package com.example.coupleapp.device.controller;

import com.example.coupleapp.common.security.CurrentUserService;
import com.example.coupleapp.device.dto.RegisterDeviceRequest;
import com.example.coupleapp.device.service.DeviceService;
import com.example.coupleapp.device.dto.UnregisterDeviceRequest;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    private final CurrentUserService currentUserService;
    private final DeviceService deviceService;

    public DeviceController(CurrentUserService currentUserService, DeviceService deviceService) {
        this.currentUserService = currentUserService;
        this.deviceService = deviceService;
    }

    @org.springframework.web.bind.annotation.PostMapping
    public Map<String, String> register(@Valid @RequestBody RegisterDeviceRequest request) {
        return deviceService.register(currentUserService.userId(), request);
    }

    @DeleteMapping
    public Map<String, String> unregister(@Valid @RequestBody UnregisterDeviceRequest request) {
        return deviceService.unregister(currentUserService.userId(), request.fcmToken());
    }
}
