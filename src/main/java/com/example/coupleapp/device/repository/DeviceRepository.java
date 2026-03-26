package com.example.coupleapp.device.repository;

import com.example.coupleapp.device.entity.Device;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Long> {

    Optional<Device> findByFcmToken(String fcmToken);

    List<Device> findByUserId(Long userId);

    void deleteByUserIdAndFcmToken(Long userId, String fcmToken);
}
