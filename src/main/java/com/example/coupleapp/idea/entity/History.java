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
@Table(name = "history")
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "idee_de", nullable = false)
    private UUID ideeDe;

    @Column(name = "termine_par", nullable = false)
    private UUID terminePar;

    @Column(name = "idee", nullable = false)
    private Long idee;

    @Column(name = "revue_de_proprietaire")
    private Long revueDeProprietaire;

    @Column(name = "revue_de_tireur")
    private Long revueDeTireur;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "reel_date", nullable = false)
    private Instant reelDate;

    @PrePersist
    void onCreate() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }
}
