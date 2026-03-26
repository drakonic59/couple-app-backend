package com.example.coupleapp.device.service;

import com.example.coupleapp.device.dto.RegisterDeviceRequest;
import com.example.coupleapp.device.entity.Device;
import com.example.coupleapp.device.repository.DeviceRepository;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Transactional
    public Device upsert(UUID currentUserId, RegisterDeviceRequest request) {
        Device device = deviceRepository.findByFcmToken(request.fcmToken()).orElseGet(Device::new);
        device.setUserId(currentUserId);
        device.setFcmToken(request.fcmToken());
        device.setPlatform(request.platform());
        device.setAppVersion(request.appVersion());
        return deviceRepository.save(device);
    }

    @Transactional
    public Map<String, String> register(UUID currentUserId, RegisterDeviceRequest request) {
        upsert(currentUserId, request);
        return Map.of("message", "Device registered");
    }

    @Transactional
    public Map<String, String> unregister(UUID currentUserId, String fcmToken) {
        deviceRepository.deleteByUserIdAndFcmToken(currentUserId, fcmToken);
        return Map.of("message", "Device unregistered");
    }
}
