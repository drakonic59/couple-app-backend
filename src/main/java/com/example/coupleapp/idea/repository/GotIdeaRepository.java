package com.example.coupleapp.idea.repository;

import com.example.coupleapp.idea.entity.GotIdea;
import com.example.coupleapp.idea.entity.GotIdea.GotIdeaId;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GotIdeaRepository extends JpaRepository<GotIdea, GotIdeaId> {
    List<GotIdea> findByUser(UUID user);
}
