package com.example.coupleapp.couple.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "couple_links")
public class CoupleLink {

    @Id
    private UUID id;

    @Column(name = "user_1_id", nullable = false)
    private UUID user1Id;

    @Column(name = "user_2_id", nullable = false)
    private UUID user2Id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CoupleStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    void onCreate() {
        if (id == null) {
            id = UUID.randomUUID();
        }
        if (status == null) {
            status = CoupleStatus.PENDING;
        }
        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }
}
