package com.example.coupleapp.user.repository;

import com.example.coupleapp.user.entity.UserSettings;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSettingsRepository extends JpaRepository<UserSettings, UUID> {
}
