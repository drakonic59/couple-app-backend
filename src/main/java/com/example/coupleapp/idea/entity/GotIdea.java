package com.example.coupleapp.idea.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "got_ideas")
@IdClass(GotIdea.GotIdeaId.class)
public class GotIdea {

    @Id
    @Column(name = "user", nullable = false)
    private UUID user;

    @Id
    @Column(name = "idea", nullable = false)
    private Long idea;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    void onCreate() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }

    public static class GotIdeaId implements Serializable {
        private UUID user;
        private Long idea;

        public GotIdeaId() {}

        public GotIdeaId(UUID user, Long idea) {
            this.user = user;
            this.idea = idea;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GotIdeaId that)) return false;
            return Objects.equals(user, that.user) && Objects.equals(idea, that.idea);
        }

        @Override
        public int hashCode() {
            return Objects.hash(user, idea);
        }
    }
}
