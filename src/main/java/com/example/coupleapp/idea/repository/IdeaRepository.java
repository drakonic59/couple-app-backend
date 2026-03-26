package com.example.coupleapp.idea.repository;

import com.example.coupleapp.idea.entity.Idea;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdeaRepository extends JpaRepository<Idea, Long> {
    List<Idea> findByOwnerAndRemovedFalse(UUID owner);
}
