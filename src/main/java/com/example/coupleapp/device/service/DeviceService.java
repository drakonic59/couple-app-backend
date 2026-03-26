package com.example.coupleapp.device.service;

import com.example.coupleapp.device.dto.RegisterDeviceRequest;
import com.example.coupleapp.device.entity.Device;
import com.example.coupleapp.device.repository.DeviceRepository;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Transactional
    public Map<String, String> register(Long currentUserId, RegisterDeviceRequest request) {
        Device device = deviceRepository.findByFcmToken(request.fcmToken()).orElseGet(Device::new);
        device.setUserId(currentUserId);
        device.setFcmToken(request.fcmToken());
        device.setPlatform(request.platform());
        deviceRepository.save(device);
        return Map.of("message", "Device registered");
    }

    @Transactional
    public Map<String, String> unregister(Long currentUserId, String fcmToken) {
        deviceRepository.deleteByUserIdAndFcmToken(currentUserId, fcmToken);
        return Map.of("message", "Device unregistered");
    }
}
