package com.example.coupleapp.device.repository;

import com.example.coupleapp.device.entity.Device;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, UUID> {

    Optional<Device> findByFcmToken(String fcmToken);

    List<Device> findByUserId(UUID userId);

    void deleteByUserIdAndFcmToken(UUID userId, String fcmToken);
}
