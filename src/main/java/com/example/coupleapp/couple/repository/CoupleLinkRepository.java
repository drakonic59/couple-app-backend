package com.example.coupleapp.couple.repository;

import com.example.coupleapp.couple.entity.CoupleLink;
import com.example.coupleapp.couple.entity.CoupleStatus;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CoupleLinkRepository extends JpaRepository<CoupleLink, UUID> {

    Optional<CoupleLink> findByStatusAndUser1IdAndUser2Id(CoupleStatus status, UUID user1Id, UUID user2Id);

    List<CoupleLink> findByStatusAndUser2Id(CoupleStatus status, UUID user2Id);

    @Query("""
            select c from CoupleLink c
            where c.status = :status and (c.user1Id = :userId or c.user2Id = :userId)
            """)
    Optional<CoupleLink> findByStatusAndUserId(CoupleStatus status, UUID userId);

    boolean existsByStatusAndUser1IdAndUser2Id(CoupleStatus status, UUID user1Id, UUID user2Id);
}
