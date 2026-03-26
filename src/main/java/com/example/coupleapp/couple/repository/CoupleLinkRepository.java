package com.example.coupleapp.couple.repository;

import com.example.coupleapp.couple.entity.CoupleLink;
import com.example.coupleapp.couple.entity.CoupleStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CoupleLinkRepository extends JpaRepository<CoupleLink, Long> {

    Optional<CoupleLink> findByStatusAndUser1IdAndUser2Id(CoupleStatus status, Long user1Id, Long user2Id);

    List<CoupleLink> findByStatusAndUser2Id(CoupleStatus status, Long user2Id);

    @Query("""
            select c from CoupleLink c
            where c.status = :status and (c.user1Id = :userId or c.user2Id = :userId)
            """)
    Optional<CoupleLink> findByStatusAndUserId(CoupleStatus status, Long userId);

    boolean existsByStatusAndUser1IdAndUser2Id(CoupleStatus status, Long user1Id, Long user2Id);
}
