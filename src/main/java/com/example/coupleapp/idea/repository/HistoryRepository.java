package com.example.coupleapp.idea.repository;

import com.example.coupleapp.idea.entity.History;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<History, Long> {
    List<History> findByIdeeDeOrTermineParOrderByCreatedAtDesc(UUID ideeDe, UUID terminePar);
}
