package com.example.coupleapp.idea.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Table(name = "ideas")
public class Idea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "owner", nullable = false)
    private UUID owner;

    @Column(name = "value", nullable = false)
    private String value;

    @Column(name = "removed", nullable = false)
    private boolean removed;

    @Column(name = "got", nullable = false)
    private boolean got;

    @Column(name = "finished", nullable = false)
    private boolean finished;

    @Column(name = "finished_by")
    private UUID finishedBy;

    @Column(name = "thrown", nullable = false)
    private boolean thrown;

    @Column(name = "thrown_by")
    private UUID thrownBy;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "remove_date")
    private Instant removeDate;

    @Column(name = "got_date")
    private Instant gotDate;

    @Column(name = "finished_date")
    private Instant finishedDate;

    @Column(name = "thrown_date")
    private Instant thrownDate;

    @PrePersist
    void onCreate() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }
}
